package com.xu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xu.user.mapper")
public class SfzyProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfzyProjectApplication.class, args);
    }

}
