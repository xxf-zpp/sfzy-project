package com.xu.user.domain.entity;

import lombok.Data;

@Data
public class SmsResult {
    // 接口原始返回字符串
    private String rawResponse;
    // 是否发送成功
    private Boolean success;
    // 提示信息
    private String msg;
}