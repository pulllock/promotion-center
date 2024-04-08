package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RuleScope {
    PRODUCT(1, "商品"),
    SELLER(2, "商户"),
    CATEGORY(3, "类目"),
    ;

    private final int scope;

    private final String desc;

    RuleScope(int scope, String desc) {
        this.scope = scope;
        this.desc = desc;
    }

    public static RuleScope of(int scope) {
        return Arrays.stream(values()).filter(s -> s.scope == scope).findFirst().orElse(null);
    }

}
