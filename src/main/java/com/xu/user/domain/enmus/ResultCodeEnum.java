package com.xu.user.domain.enmus;

import lombok.Getter;

/**
 * 全局响应状态码枚举
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    PARAM_ERROR(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或token失效"),
    FORBIDDEN(403, "权限不足，禁止访问"),
    NOT_FOUND(404, "资源不存在");

    /** 响应码 */
    private final Integer code;
    /** 响应信息 */
    private final String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}