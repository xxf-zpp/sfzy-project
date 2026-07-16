package com.xu.user.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private final Integer code;
    private final String msg;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg) {
        this(500, msg);
    }

    public Integer getCode() { return code; }

    @Override
    public String getMessage() { return msg; }

}
