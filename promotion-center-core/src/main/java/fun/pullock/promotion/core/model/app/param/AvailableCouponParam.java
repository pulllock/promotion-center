package fun.pullock.promotion.core.model.app.param;

import lombok.Data;

@Data
public class AvailableCouponParam {

    private Long spuId;

    private Integer couponType;

    private String channel;
}
