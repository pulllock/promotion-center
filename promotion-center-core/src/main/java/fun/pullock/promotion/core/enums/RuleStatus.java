package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RuleStatus {
    DISABLE(0, "禁用"),
    ENABLE(1, "启用"),
    ;

    private final int status;

    private final String desc;

    RuleStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static RuleStatus of(int status) {
        return Arrays.stream(values()).filter(t -> t.status == status).findFirst().orElse(null);
    }

}
