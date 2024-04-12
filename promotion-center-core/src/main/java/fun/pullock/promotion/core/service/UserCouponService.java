package fun.pullock.promotion.core.service;

import fun.pullock.general.model.ServiceException;
import fun.pullock.promotion.api.enums.ErrorCode;
import fun.pullock.promotion.core.dao.mapper.UserCouponMapper;
import fun.pullock.promotion.core.dao.model.UserCouponDO;
import fun.pullock.promotion.core.enums.CouponValidityType;
import fun.pullock.promotion.core.enums.UserCouponStatus;
import fun.pullock.promotion.core.model.app.param.ClaimCouponParam;
import fun.pullock.promotion.core.model.dto.CouponRuleDTO;
import fun.pullock.promotion.core.model.dto.UserCouponDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCouponService {

    @Resource
    private UserCouponMapper userCouponMapper;

    public List<UserCouponDTO> queryUserCoupons(Long userId, Long couponId) {
        return userCouponMapper.selectByUserCouponId(userId, couponId)
                .stream()
                .map(this::toUserCouponDTO)
                .collect(Collectors.toList());
    }

    public void add(Long userId, ClaimCouponParam param, CouponRuleDTO rule) {
        try {
            LocalDateTime now = LocalDateTime.now();
            UserCouponDO userCouponDO = new UserCouponDO();
            userCouponDO.setCreateTime(now);
            userCouponDO.setUpdateTime(now);
            userCouponDO.setUserId(userId);
            userCouponDO.setCouponId(param.getCouponId());
            userCouponDO.setStatus(UserCouponStatus.UNUSED.getStatus());
            userCouponDO.setClaimTime(now);
            if (rule.getValidityType() == CouponValidityType.FIXED.getType()) {
                userCouponDO.setValidityStartTime(rule.getValidityStartTime());
                userCouponDO.setValidityEndTime(rule.getValidityEndTime());
            }
            else {
                userCouponDO.setValidityStartTime(now);
                userCouponDO.setValidityEndTime(
                        now.plusDays(rule.getValidityDays()).withHour(23).withMinute(59).withSecond(59)
                );
            }
            userCouponDO.setThreshold(rule.getThreshold());
            userCouponDO.setDiscount(rule.getDiscount());
            userCouponDO.setChannel(param.getChannel());
            userCouponDO.setSource(param.getSource());
            userCouponDO.setUniqueSourceId(param.getUniqueSourceId());
            userCouponMapper.insertSelective(userCouponDO);
        } catch (DuplicateKeyException e) {
            throw new ServiceException(ErrorCode.CONCURRENCY_ERROR);
        }
    }

    private UserCouponDTO toUserCouponDTO(UserCouponDO source) {
        if (source == null) {
            return null;
        }

        UserCouponDTO target = new UserCouponDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
