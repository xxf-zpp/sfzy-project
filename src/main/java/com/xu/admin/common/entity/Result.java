package com.xu.admin.common.entity;


import com.xu.admin.common.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class Result<T> {
    // 响应码
    private Integer code;
    // 响应提示信息
    private String msg;
    // 返回业务数据
    private T data;

    // ---------------------- 私有构造，只能通过静态方法创建对象 ----------------------
    private Result() {}

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // ======================== 成功静态方法 ========================
    /**
     * 成功，无返回数据
     */
    public static <T> Result<T> ok() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), null);
    }

    /**
     * 成功，携带返回数据
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功，自定义提示信息 + 数据
     */
    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), msg, data);
    }

    // ======================== 失败静态方法 ========================
    /**
     * 默认失败
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMsg(), null);
    }

    /**
     * 自定义失败提示
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), msg, null);
    }

    /**
     * 根据枚举返回对应错误
     */
    public static <T> Result<T> fail(ResultCodeEnum codeEnum) {
        return new Result<>(codeEnum.getCode(), codeEnum.getMsg(), null);
    }

    /**
     * 自定义错误码+错误信息
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}
