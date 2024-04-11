package fun.pullock.promotion.core.service;

import fun.pullock.promotion.core.model.app.param.AvailableCouponParam;
import fun.pullock.promotion.core.model.app.result.AvailableCoupon;
import fun.pullock.promotion.core.model.dto.*;
import fun.pullock.promotion.core.proxy.product.ProductClientService;
import fun.pullock.promotion.core.proxy.product.model.MockProductDTO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static fun.pullock.promotion.core.enums.UserCouponStatus.UNUSED;

@Service
public class CouponService {

    @Resource
    private ProductClientService productClientService;

    @Resource
    private RuleTargetService ruleTargetService;

    @Resource
    private RuleService ruleService;

    @Resource
    private UserCouponService userCouponService;

    public List<AvailableCoupon> availableCoupons(Long userId, AvailableCouponParam param) {
        // 根据参数查询出的所有可用的优惠券
        // 根据spu id查询对应的sku id、商户ID、类目
        MockProductDTO product = productClientService.queryBySpu(param.getSpuId());
        List<Long> skuIds = product.getSkus()
                .stream()
                .map(MockProductDTO.MockProductSkuDTO::getId)
                .collect(Collectors.toList());
        Long sellerId = product.getSellerId();
        List<Long> categoryIds = product.getCategories()
                .stream()
                .map(MockProductDTO.MockProductCategoryDTO::getId)
                .collect(Collectors.toList());

        // 查询优惠券规则对象
        List<RuleTargetDTO> couponTargets = ruleTargetService.queryCouponTargets(
                skuIds, Collections.singletonList(sellerId), categoryIds
        );
        if (CollectionUtils.isEmpty(couponTargets)) {
            return null;
        }

        // 所有可用优惠券
        List<CouponRuleDTO> coupons = couponTargets
                .stream()
                .map(RuleTargetDTO::getRuleId)
                .distinct()
                .map(i -> ruleService.queryById(i))
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
