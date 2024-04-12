package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CouponValidityType {
    FIXED(1, "固定有效期"),
    DYNAMIC(2, "动态有效期（领取/发放后N天有效）"),
    ;

    private final int type;

    private final String desc;

    CouponValidityType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CouponValidityType of(int type) {
        return Arrays.stream(values()).filter(t -> t.type == type).findFirst().orElse(null);
    }

}
