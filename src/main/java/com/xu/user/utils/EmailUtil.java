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
 * 邮件发送工具类（阿里云API市场，main方法直接运行）
 */
public class EmailUtil {

    public static void main(String[] args) {
        String host = "https://jmyjts.market.alicloudapi.com";
        String path = "/email/send";
        String appcode = "ecf82a3e361943018b0273b521d31fbd";   // ← 替换为你的AppCode

        RestTemplate restTemplate = new RestTemplate();

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + appcode);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 表单参数（POST body: application/x-www-form-urlencoded）
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("templateId", "JMVOKAKXQEH5");           // ← 邮件模板ID
        body.add("toAddress", "2265035472@qq.com");              // ← 收件人地址
        body.add("subject", "subject");                  // ← 邮件主题
        body.add("values", "666666");                    // ← 模板变量值
        body.add("fromAlias", "基佬");              // ← 发件人别名

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + path,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    String.class);
            System.out.println("邮件返回：" + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
