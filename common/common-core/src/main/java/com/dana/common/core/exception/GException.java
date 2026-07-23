package com.dana.common.core.exception;

import com.dana.common.core.web.ResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class GException extends RuntimeException {

    private final int code;

    /**
     * 基于状态码枚举构造
     */
    public GException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    /**
     * 自定义状态码 + 提示语
     */
    public GException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 自定义提示语（默认 500 状态码）
     */
    public GException(String msg) {
        super(msg);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
    }
}
