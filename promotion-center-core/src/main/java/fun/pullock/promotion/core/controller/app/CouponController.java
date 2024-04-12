package fun.pullock.promotion.core.controller.app;

import fun.pullock.general.model.ServiceException;
import fun.pullock.promotion.core.enums.CouponType;
import fun.pullock.promotion.core.model.app.param.AvailableCouponParam;
import fun.pullock.promotion.core.model.app.param.ClaimCouponParam;
import fun.pullock.promotion.core.model.app.result.AvailableCoupon;
import fun.pullock.promotion.core.service.CouponService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static fun.pullock.promotion.api.enums.ErrorCode.PARAM_ERROR;

@RestController
@RequestMapping("/app/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @PostMapping("/list/available")
    public List<AvailableCoupon> availableCoupons(@RequestBody AvailableCouponParam param) {
        Long userId = 1L;
        if (param == null) {
            throw new ServiceException(PARAM_ERROR);
        }

        if (param.getSpuId() == null) {
            throw new ServiceException(PARAM_ERROR, "SPU ID不能为空");
        }

        if (CouponType.of(param.getCouponType()) == null) {
            throw new ServiceException(PARAM_ERROR, "未知的优惠券类型");
        }

        return couponService.availableCoupons(userId, param);
    }

    @PostMapping("/claim")
    public Boolean claim(@RequestBody ClaimCouponParam param) {
        Long userId = 1L;

        if (param == null) {
            throw new ServiceException(PARAM_ERROR);
        }

        if (param.getCouponId() == null) {
            throw new ServiceException(PARAM_ERROR, "优惠券ID为空");
        }

        if (StringUtils.isAnyEmpty(param.getSource(), param.getUniqueSourceId())) {
            throw new ServiceException(PARAM_ERROR, "source或者source id为空");
        }

        return couponService.claim(userId, param);
    }
}
