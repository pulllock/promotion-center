package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CouponType {
    COUPON(1, "优惠券"),
    RED_PACKET(2, "红包"),
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
