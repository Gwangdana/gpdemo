package com.famsun.momapi.core.constent;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权/令牌过期"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    // 服务端错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
