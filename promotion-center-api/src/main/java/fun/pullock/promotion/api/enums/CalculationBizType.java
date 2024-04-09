package fun.pullock.promotion.api.enums;


import java.util.Arrays;

public enum CalculationBizType {
    COUPON(1, "通用"),
    DYNAMIC_COUPON(2, "优惠券凑单"),
    PROMO_CODE(3, "满件折凑单"),
    RED_PACKET(4, "包邮凑单"),
    DYNAMIC_RED_PACKET(5, "购物车"),
    ;

    private final int type;

    private final String desc;

    CalculationBizType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CalculationBizType of(int type) {
        return Arrays.stream(values()).filter(t -> t.type == type).findFirst().orElse(null);
    }

    public String getDesc() {
        return desc;
    }

    public int getType() {
        return type;
    }
}
