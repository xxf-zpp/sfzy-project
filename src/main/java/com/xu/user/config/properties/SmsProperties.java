package com.xu.user.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties {
    // 请求域名
    private String host;
    // 请求路径
    private String path;
    // AppCode
    private String appCode;
    // 短信模板ID
    private String templateId;
}