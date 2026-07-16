package com.xu.user.config;

import com.xu.user.domain.entity.Result;
import com.xu.user.domain.enmus.ResultCodeEnum;
import com.xu.user.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ======================== 业务异常 ========================

    /**
     * 自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常 [code={}]：{}", e.getCode(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    // ======================== 数据库约束异常 ========================

    /**
     * 唯一约束冲突（如角色编码重复、用户名重复等）
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<?> handleSQLIntegrityConstraintViolation(SQLIntegrityConstraintViolationException e) {
        log.warn("唯一约束冲突：{}", e.getMessage());
        // 从原始异常信息中解析出友好提示
        String msg = parseDuplicateMsg(e.getMessage());
        return Result.fail(ResultCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 数据完整性异常（Spring包装，兜底）
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.warn("数据完整性异常：{}", e.getMessage());
        // 尝试从原始cause中获取更具体的提示
        Throwable cause = e.getMostSpecificCause();
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            String msg = parseDuplicateMsg(cause.getMessage());
            return Result.fail(ResultCodeEnum.PARAM_ERROR.getCode(), msg);
        }
        return Result.fail(ResultCodeEnum.PARAM_ERROR.getCode(), "数据保存失败：数据已存在或违反数据库约束");
    }

    // ======================== 参数异常 ========================

    /**
     * 参数校验失败
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("参数校验失败：{}", e.getMessage());
        return Result.fail(ResultCodeEnum.PARAM_ERROR.getCode(), "参数校验失败：" + e.getMessage());
    }

    // ======================== 系统异常 ========================

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常", e);
        return Result.fail(ResultCodeEnum.FAIL.getCode(), "系统内部错误：空指针异常，请联系管理员");
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("未捕获的系统异常 [{}]：{}", e.getClass().getSimpleName(), e.getMessage(), e);
        return Result.fail(ResultCodeEnum.FAIL.getCode(), "系统繁忙，请稍后重试");
    }

    // ======================== 工具方法 ========================

    /**
     * 从SQL异常信息中提取友好提示。
     * <pre>
     * 输入："Duplicate entry 'test' for key 'sys_role.uk_role_code'"
     * 输出："角色编码 [test] 已存在，请更换后重试"
     *
     * 输入："Duplicate entry 'admin' for key 'sys_user.uk_username'"
     * 输出："用户名 [admin] 已存在，请更换后重试"
     * </pre>
     */
    private String parseDuplicateMsg(String msg) {
        if (msg == null || msg.isEmpty()) {
            return "数据已存在，请更换后重试";
        }
        try {
            String duplicateValue = null;
            String constraintKey = null;

            // 提取重复值：Duplicate entry 'xxx'
            int dupStart = msg.indexOf("Duplicate entry '");
            if (dupStart > -1) {
                dupStart += 17;
                int dupEnd = msg.indexOf("'", dupStart);
                if (dupEnd > -1) {
                    duplicateValue = msg.substring(dupStart, dupEnd);
                }
            }

            // 提取约束字段名：key 'table.column'
            int keyStart = msg.indexOf("key '");
            if (keyStart > -1) {
                keyStart += 5;
                int keyEnd = msg.indexOf("'", keyStart);
                if (keyEnd > -1) {
                    String fullKey = msg.substring(keyStart, keyEnd);
                    constraintKey = fullKey.contains(".") ? fullKey.substring(fullKey.indexOf(".") + 1) : fullKey;
                }
            }

            // 组装友好提示
            if (constraintKey != null && duplicateValue != null) {
                String fieldName = constraintToFieldName(constraintKey);
                if (fieldName != null) {
                    return fieldName + " [" + duplicateValue + "] 已存在，请更换后重试";
                }
                return "数据 [" + duplicateValue + "] 已存在（" + constraintKey + "），请更换后重试";
            }
            if (constraintKey != null) {
                String fieldName = constraintToFieldName(constraintKey);
                if (fieldName != null) {
                    return fieldName + "已存在，请更换后重试";
                }
                return "数据重复（" + constraintKey + "），请更换后重试";
            }
        } catch (Exception ignored) {}
        return "数据已存在，请更换后重试";
    }

    /**
     * 数据库字段名 → 中文名映射
     */
    private String constraintToFieldName(String constraint) {
        return switch (constraint) {
            case "uk_role_code"   -> "角色编码";
            case "uk_role_name"   -> "角色名称";
            case "uk_username"    -> "用户名";
            case "uk_phone"       -> "手机号";
            case "uk_email"       -> "邮箱";
            default               -> null;  // 未配置映射时用原始字段名
        };
    }

}
