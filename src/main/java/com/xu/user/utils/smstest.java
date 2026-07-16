package com.xu.user.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 创信短信测试（main方法直接运行，无需Spring容器）
 */
public class Smstest {

    public static void main(String[] args) {
        String host = "https://cxkjsms.market.alicloudapi.com";
        String path = "/chuangxinsms/dxjk";
        String appcode = "ecf82a3e361943018b0273b521d31fbd";

        RestTemplate restTemplate = new RestTemplate();

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + appcode);

        // 拼接参数（自动URL编码中文）
        String fullUrl = UriComponentsBuilder.fromUriString(host + path)
                .queryParam("content", "【创信】你的验证码是：6666，3分钟内有效！")
                .queryParam("mobile", "18488039825")
                .build()
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl, HttpMethod.POST, new HttpEntity<>(headers), String.class);
            System.out.println("短信返回：" + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
