package com.xu.TestController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/base")
public class Test {

    @GetMapping("/test")
    public String test(){
        log.info("测试......");
        return "你好！！！";
    }

}
