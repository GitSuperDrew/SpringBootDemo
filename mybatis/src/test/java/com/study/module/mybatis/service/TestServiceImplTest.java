package com.study.module.mybatis.service;

import com.study.module.mybatis.vo.DecryptResponse;
import com.study.module.mybatis.vo.EncryptRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Administrator
 * @date 2020/8/23 下午 6:12
 */
@Slf4j
@SpringBootTest
class TestServiceImplTest {

    @Autowired
    private TestServiceImpl testService;

    @Test
    void testEncrypt() {
        EncryptRequest request = new EncryptRequest();
        request.setBankCardNo("54323453454353");
        request.setIdCard("432665477876549876");
        request.setName("Drew");
        request.setSex("男");

        testService.testEncrypt(request);
    }

    @Test
    void testDecrypt() {
        EncryptRequest request = new EncryptRequest();
        request.setBankCardNo("B3E06aLDq4lmCUseHBvsrg==");
        request.setIdCard("NefQtZKGjOHhxozn3QEAFZQ3uJi1T/V7QKkS6K5U93M=");
        request.setName("Drew");
        request.setSex("男");

        DecryptResponse response = testService.testDecrypt(request);
        log.info("\n解密响应：{}" , response.toString());
    }
}
