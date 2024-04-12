package fun.pullock.promotion.core.model.app.param;

import lombok.Data;

@Data
public class ClaimCouponParam {

    private Long couponId;

    private String channel;

    private String source;

    private String uniqueSourceId;
}
