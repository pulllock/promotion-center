package fun.pullock.promotion.core.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserCouponStatus {
    UNUSED(1, "未使用"),
    USED(2, "已使用"),
    EXPIRED(3, "已过期"),
    DELETED(4, "已删除")
    ;

    private final int status;

    private final String desc;

    UserCouponStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static UserCouponStatus of(int status) {
        return Arrays.stream(values()).filter(t -> t.status == status).findFirst().orElse(null);
    }

}
