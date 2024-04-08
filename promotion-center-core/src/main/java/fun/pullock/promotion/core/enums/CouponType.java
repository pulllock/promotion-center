package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CouponType {
    COUPON(1, "优惠券"),
    DYNAMIC_COUPON(2, "动态优惠券"),
    PROMO_CODE(3, "优惠码"),
    RED_PACKET(4, "红包"),
    DYNAMIC_RED_PACKET(5, "动态红包"),
    ;

    private final int type;

    private final String desc;

    CouponType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CouponType of(int type) {
        return Arrays.stream(values()).filter(t -> t.type == type).findFirst().orElse(null);
    }

}
