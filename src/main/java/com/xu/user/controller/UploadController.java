package com.xu.user.controller;

import com.xu.user.domain.entity.Result;
import com.xu.user.utils.OssUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final OssUtil ossUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping
    public Result<?> upload(MultipartFile file) {
        String avatarUrl = ossUtil.uploadFile(file);
        return Result.ok(avatarUrl);
    }

}
