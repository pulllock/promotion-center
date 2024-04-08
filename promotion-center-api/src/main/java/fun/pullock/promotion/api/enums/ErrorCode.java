package fun.pullock.promotion.api.enums;

import fun.pullock.general.model.BaseErrorCode;

/**
 * 错误码
 * 通用错误码范围：0-9999，其中0表示成功
 * 具体的业务错误码从10000开始
 */
public enum ErrorCode implements BaseErrorCode {

    SYSTEM_ERROR(1, "系统错误"),
    PARAM_ERROR(2, "参数错误"),
    CONCURRENCY_ERROR(3, "并发错误"),

    ;

    ErrorCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private final int errorCode;

    private final String errorMsg;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
