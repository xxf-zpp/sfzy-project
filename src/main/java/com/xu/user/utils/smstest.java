package com.xu.user.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 创信短信测试（main方法直接运行，无需Spring容器）
 */
class Smstest {

    public static void main(String[] args) {
        String host = "https://jumsendv2.market.alicloudapi.com";
        String path = "/sms/send-v2";
        String appcode = "ecf82a3e361943018b0273b521d31fbd";   // ← 替换为你的AppCode

        RestTemplate restTemplate = new RestTemplate();

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + appcode);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 表单参数（POST body: application/x-www-form-urlencoded）
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("mobile", "18488039825");           // ← 手机号
        body.add("templateId", "JM1000372");        // ← 模板ID
        body.add("value", "666666");                 // ← 验证码

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + path,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    String.class);
            System.out.println("短信返回：" + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
