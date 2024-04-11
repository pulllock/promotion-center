package fun.pullock.promotion.core.service;

import fun.pullock.promotion.core.dao.mapper.UserCouponMapper;
import fun.pullock.promotion.core.dao.model.UserCouponDO;
import fun.pullock.promotion.core.model.dto.UserCouponDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    private UserCouponDTO toUserCouponDTO(UserCouponDO source) {
        if (source == null) {
            return null;
        }

        UserCouponDTO target = new UserCouponDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
