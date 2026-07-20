package com.xu.user.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云OSS文件上传工具类
 */
@Slf4j
@Component
public class OssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${aliyun.oss.domain}")
    private String domain;

    /**
     * 上传MultipartFile文件到OSS
     * @param file 前端上传文件
     * @return 文件完整访问URL
     */
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new RuntimeException("文件名称不能为空");
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + UUID.randomUUID() + suffix;

        OSS ossClient = null;
        try (InputStream inputStream = file.getInputStream()) {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, fileName, inputStream);
            return domain + "/" + fileName;
        } catch (IOException e) {
            log.error("OSS文件上传失败，IO异常", e);
            throw new RuntimeException("文件上传失败", e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 适配前端base64图片上传
     * @param base64Str
     * @return OSS文件访问URL
     */
    public String uploadBase64(String avatarBase64) {
        // 1. 切割base64前缀
        String[] split = avatarBase64.split(",");
        if (split.length < 2) {
            throw new RuntimeException("图片base64格式错误");
        }
        String suffix = split[0].split("/")[1].split(";")[0]; // 提取png/jpg/jpeg
        String pureBase64 = split[1];

        // 2. 生成oss存储路径
        String fileName = new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + UUID.randomUUID() + "." + suffix;

        OSS ossClient = null;
        byte[] imgBytes;
        try {
            // base64解码为字节数组
            imgBytes = Base64.getDecoder().decode(pureBase64);
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传字节数组到OSS
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(imgBytes));
            return domain + "/" + fileName;
        } catch (Exception e) {
            log.error("Base64图片上传OSS失败", e);
            throw new RuntimeException("图片上传失败", e);
        } finally {
            if (ossClient != null) ossClient.shutdown();
        }
    }

    /**
     * 删除OSS文件
     * @param fileUrl 文件完整访问URL
     */
    public void deleteFile(String fileUrl) {
        String filePath = fileUrl.replace(domain + "/", "");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucketName, filePath);
        } catch (Exception e) {
            log.error("OSS文件删除失败", e);
            throw new RuntimeException("文件删除失败", e);
        } finally {
            ossClient.shutdown();
        }
    }

}