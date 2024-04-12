package fun.pullock.promotion.core.service;

import fun.pullock.general.model.ServiceException;
import fun.pullock.promotion.core.dao.mapper.CouponRuleMapper;
import fun.pullock.promotion.core.enums.RuleStatus;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.app.param.AvailableCouponParam;
import fun.pullock.promotion.core.model.app.param.ClaimCouponParam;
import fun.pullock.promotion.core.model.app.result.AvailableCoupon;
import fun.pullock.promotion.core.model.dto.CouponRuleDTO;
import fun.pullock.promotion.core.model.dto.RuleTargetDTO;
import fun.pullock.promotion.core.proxy.product.ProductClientService;
import fun.pullock.promotion.core.proxy.product.model.MockProductDTO;
import fun.pullock.starter.redis.lock.RedisLock;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static fun.pullock.promotion.api.enums.ErrorCode.*;
import static fun.pullock.promotion.core.enums.UserCouponStatus.UNUSED;

@Service
public class CouponService {

    @Resource
    private ProductClientService productClientService;

    @Resource
    private RuleTargetService ruleTargetService;

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private CouponRuleService couponRuleService;

    @Resource
    private CouponCounterService couponCounterService;

    @Resource
    private RedisLock redisLock;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private CouponRuleMapper couponRuleMapper;

    public List<AvailableCoupon> availableCoupons(Long userId, AvailableCouponParam param) {
        // 根据参数查询出的所有可用的优惠券
        List<RuleTargetDTO> couponTargets = queryCouponTargets(param);
        if (CollectionUtils.isEmpty(couponTargets)) {
            return null;
        }

        // 所有可用优惠券
        List<CouponRuleDTO> coupons = couponTargets
                .stream()
                .map(RuleTargetDTO::getRuleId)
                .distinct()
                .map(i -> couponRuleService.queryById(i))
                .filter(c -> c.getValidityEndTime() == null || LocalDateTime.now().isBefore(c.getValidityEndTime()))
                .sorted(Comparator.comparing(CouponRuleDTO::getDiscount, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // 用户已领取/发放的优惠券
        List<CouponRuleDTO> claimed = coupons
                .stream()
                .filter(c -> userCouponService.queryUserCoupons(userId, c.getRuleId())
                        .stream()
                        .anyMatch(uc -> uc.getStatus() == UNUSED.getStatus() && LocalDateTime.now().isBefore(uc.getValidityEndTime()))
                )
                .sorted(Comparator.comparing(CouponRuleDTO::getDiscount, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        List<AvailableCoupon> availableCoupons = new ArrayList<>();
        availableCoupons.addAll(coupons.stream().map(this::toAvailableCoupon).collect(Collectors.toList()));
        availableCoupons.addAll(claimed.stream().map(c -> {
            AvailableCoupon ac = toAvailableCoupon(c);
            ac.setClaimed(true);
            return ac;
        }).collect(Collectors.toList()));
        return availableCoupons;
    }

    public Boolean claim(Long userId, ClaimCouponParam param) {
        LocalDateTime now = LocalDateTime.now();

        // 查询并预校验优惠券规则
        CouponRuleDTO rule = preCheckRule(param, now);

        // 校验已领取数量
        checkClaimed(param, rule);

        // 加锁
        String uKey = String.format("COUPON_CLAIM_REQUEST_LOCK_%s_%s", userId, param.getCouponId());
        boolean locked = redisLock.tryLock(uKey, 1000 * 60);
        if (!locked) {
            throw new ServiceException(CONCURRENCY_ERROR, "请求太快了");
        }

        try {
            return claim(userId, param, now, rule);
        } finally {
            redisLock.unlock(uKey);
        }
    }

    private Boolean claim(Long userId, ClaimCouponParam param, LocalDateTime now, CouponRuleDTO rule) {
        // 幂等
        duplicateCheck(userId, param);

        // 校验用户领取数量
        checkUserClaimed(userId, param, now, rule);

        return doClaim(userId, param, rule, now);
    }

    private Boolean doClaim(Long userId, ClaimCouponParam param, CouponRuleDTO rule, LocalDateTime now) {
        Boolean result = transactionTemplate.execute(status -> {
            try {
                // 记录用户优惠券
                userCouponService.add(userId, param, rule);

                String key = String.format("COUPON_CLAIM_INCREMENT_CLAIMED_LOCK_%s", param.getCouponId());
                boolean lock = redisLock.lock(key, 1000 * 60);
                if (!lock) {
                    throw new ServiceException(CONCURRENCY_ERROR, "请重试");
                }

                try {
                    // 校验已领取数量
                    checkClaimed(param, rule);

                    // 增加优惠券发放总数量
                    updateClaimed(param, rule);
                } finally {
                    redisLock.unlock(key);
                }
                return true;
            } catch (ServiceException e) {
                status.setRollbackOnly();
                throw e;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });

        // 记录用户领取数量
        updateUserClaim(userId, param, now);
        return result;
    }

    private void updateClaimed(ClaimCouponParam param, CouponRuleDTO rule) {
        Long total = couponCounterService.claim(param.getCouponId(), 1L);
        if (Objects.equals(total, rule.getTotal())) {
            couponRuleMapper.updateClaimed(rule.getId(), total);
        }
    }

    private void updateUserClaim(Long userId, ClaimCouponParam param, LocalDateTime now) {
        // 增加用户每日发放数量
        couponCounterService.userDailyClaim(userId, param.getCouponId(), 1L, now);

        // 增加用户总共发放数量
        couponCounterService.userTotalClaim(userId, param.getCouponId(), 1L);
    }

    private void checkUserClaimed(Long userId, ClaimCouponParam param, LocalDateTime now, CouponRuleDTO rule) {
        // 校验用户当日领取数量
        Long userDailyClaimed = couponCounterService.userDailyClaimed(userId, param.getCouponId(), now);
        if (userDailyClaimed != null && userDailyClaimed >= rule.getUserDailyTotal()) {
            throw new ServiceException(USER_DAILY_EXCEEDED);
        }

        // 校验用户领取总数量
        Long userTotalClaimed = couponCounterService.userTotalClaimed(userId, param.getCouponId());
        if (userTotalClaimed != null && userTotalClaimed >= rule.getUserTotal()) {
            throw new ServiceException(USER_DAILY_EXCEEDED);
        }
    }

    private void duplicateCheck(Long userId, ClaimCouponParam param) {
        String sKey = String.format("COUPON_CLAIM_REQUEST_DUPLICATE_LOCK_%s_%s_%s_%s", userId, param.getCouponId(), param.getSource(), param.getUniqueSourceId());
        boolean exist = redisLock.tryLock(sKey, 1000 * 60);
        if (!exist) {
            throw new ServiceException(CONCURRENCY_ERROR, "重复请求");
        }
    }

    private void checkClaimed(ClaimCouponParam param, CouponRuleDTO rule) {
        Long claimed = couponCounterService.claimed(param.getCouponId());
        claimed = claimed == null ? 0 : claimed;
        if (rule.getTotal() - claimed - 1 < 0) {
            throw new ServiceException(PARAM_ERROR, "已领完");
        }
    }

    private CouponRuleDTO preCheckRule(ClaimCouponParam param, LocalDateTime now) {
        // 查询优惠券规则
        CouponRuleDTO rule = couponRuleService.queryById(param.getCouponId());
        if (rule == null || rule.getStatus() != RuleStatus.ENABLE.getStatus()) {
            throw new ServiceException(PARAM_ERROR, "优惠券不存在");
        }

        if (rule.getClaimStartTime() != null && now.isBefore(rule.getClaimStartTime())) {
            throw new ServiceException(PARAM_ERROR, "未开始");
        }

        if (rule.getClaimEndTime() != null && now.isAfter(rule.getClaimEndTime())) {
            throw new ServiceException(PARAM_ERROR, "已结束");
        }

        if (rule.getValidityEndTime() != null && now.isAfter(rule.getValidityEndTime())) {
            throw new ServiceException(PARAM_ERROR, "已结束");
        }

        return rule;
    }

    private List<RuleTargetDTO> queryCouponTargets(AvailableCouponParam param) {
        // 根据spu id查询对应的sku id、商户ID、类目
        MockProductDTO product = productClientService.queryBySpu(param.getSpuId());
        List<Long> skuIds = product.getSkus()
                .stream()
                .map(MockProductDTO.MockProductSkuDTO::getId)
                .collect(Collectors.toList());
        List<Long> categoryIds = product.getCategories()
                .stream()
                .map(MockProductDTO.MockProductCategoryDTO::getId)
                .collect(Collectors.toList());

        // 查询优惠券规则对象
        return ruleTargetService.queryTargets(
                skuIds,
                Collections.singletonList(product.getSellerId()),
                categoryIds,
                RuleType.COUPON.getType()
        );
    }

    private AvailableCoupon toAvailableCoupon(CouponRuleDTO source) {
        if (source == null) {
            return null;
        }

        AvailableCoupon target = new AvailableCoupon();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setRuleDescription(source.getRuleDescription());
        target.setRedirectUrl(source.getRedirectUrl());
        target.setType(source.getType());
        target.setDynamic(source.getDynamic());
        target.setThreshold(source.getThreshold());
        target.setDiscount(source.getDiscount());
        target.setValidityType(source.getValidityType());
        target.setValidityStartTime(source.getValidityStartTime());
        target.setValidityEndTime(source.getValidityEndTime());
        target.setValidityDays(source.getValidityDays());
        target.setClaimed(false);
        return target;
    }
}
