package com.xu.user.utils;

import com.xu.user.config.properties.SmsProperties;
import com.xu.user.domain.entity.SmsResult;
import jakarta.annotation.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
public class SmsUtil {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SmsProperties smsProperties;

    /**
     * 发送短信验证码
     * @param mobile 接收手机号
     * @param code 验证码数字
     * @return 短信发送结果
     */
    public SmsResult sendVerifyCode(String mobile, String code) {
        SmsResult result = new SmsResult();
        try {
            // 1. 拼接完整请求地址
            String url = smsProperties.getHost() + smsProperties.getPath();

            // 2. 请求头设置
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "APPCODE " + smsProperties.getAppCode());
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 3. 表单请求参数
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("mobile", mobile);
            body.add("templateId", smsProperties.getTemplateId());
            body.add("value", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            // 4. 发起POST请求
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            String responseBody = response.getBody();
            result.setRawResponse(responseBody);
            result.setSuccess(true);
            result.setMsg("短信发送成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("短信发送失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}