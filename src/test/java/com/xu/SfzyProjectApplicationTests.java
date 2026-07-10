package com.xu;

import com.xu.user.service.impl.SysUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class SfzyProjectApplicationTests {

    private SysUserServiceImpl service;

    @Test
    void contextLoads() {
    }

}
