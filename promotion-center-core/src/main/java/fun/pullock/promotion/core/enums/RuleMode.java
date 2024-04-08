package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RuleMode {
    SINGLE(1, "单个"),
    ACCUMULATE(2, "累加"),
    ;

    private final int mode;

    private final String desc;

    RuleMode(int mode, String desc) {
        this.mode = mode;
        this.desc = desc;
    }

    public static RuleMode of(int mode) {
        return Arrays.stream(values()).filter(m -> m.mode == mode).findFirst().orElse(null);
    }

}
