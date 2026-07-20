package com.xu.user.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 短信发送工具类（阿里云API市场）
 */
@Slf4j
@Component
public class SmsUtil {

    @Value("${aliyun.sms.host}")
    private String host;

    @Value("${aliyun.sms.path}")
    private String path;

    @Value("${aliyun.sms.app-code}")
    private String appCode;

    @Value("${aliyun.sms.template-id}")
    private String templateId;

    @Value("${aliyun.sms.content-prefix:code:}")
    private String contentPrefix;

    private final RestTemplate restTemplate;

    public SmsUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 发送短信验证码
     *
     * @param phoneNumber 手机号
     * @param code        验证码
     * @return true 发送成功，false 发送失败
     */
    public boolean sendSms(String phoneNumber, String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + appCode);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("content", contentPrefix + code);
        body.add("template_id", templateId);
        body.add("phone_number", phoneNumber);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + path,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    String.class);
            log.info("短信发送成功，手机号：{}，响应：{}", phoneNumber, response.getBody());
            return true;
        } catch (Exception e) {
            log.error("短信发送失败，手机号：{}", phoneNumber, e);
            return false;
        }
    }
}
