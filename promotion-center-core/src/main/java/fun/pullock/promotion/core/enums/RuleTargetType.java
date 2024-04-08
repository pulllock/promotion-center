package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RuleTargetType {
    PRODUCT(1, "商品"),
    SELLER(2, "商户"),
    CATEGORY(3, "类目"),
    ;

    private final int type;

    private final String desc;

    RuleTargetType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static RuleTargetType of(int type) {
        return Arrays.stream(values()).filter(t -> t.type == type).findFirst().orElse(null);
    }

}
