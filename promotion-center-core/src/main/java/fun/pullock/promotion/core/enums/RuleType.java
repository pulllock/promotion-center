package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RuleType {
    COUPON(1, "优惠券"),
    REACH_EXEMPTION(2, "满减"),
    REACH_DISCOUNT(3, "满件折"),
    REACH_GIFT(4, "满额赠"),
    POINTS(5, "积分"),
    GOLD_COIN(6, "金币")
    ;

    private final int type;

    private final String desc;

    RuleType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static RuleType of(int type) {
        return Arrays.stream(values()).filter(t -> t.type == type).findFirst().orElse(null);
    }

}
