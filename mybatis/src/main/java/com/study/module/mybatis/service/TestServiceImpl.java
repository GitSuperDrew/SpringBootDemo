package com.study.module.mybatis.service;

import com.study.module.mybatis.aop.EncryptMethod;
import com.study.module.mybatis.vo.DecryptResponse;
import com.study.module.mybatis.vo.EncryptRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 测试服务实现类
 *
 * @author Administrator
 * @date 2020/8/23 下午 6:06
 */
@Slf4j
@Service
public class TestServiceImpl {

    @EncryptMethod
    public String testEncrypt(EncryptRequest request) {
        log.info("\ntestEncrypt 业务逻辑入参 request:{}", request.toString());
        return null;
    }

    @EncryptMethod
    public DecryptResponse testDecrypt(EncryptRequest request) {
        log.info("\ntestDecrypt 业务逻辑入参 request:{}", request.toString());
        DecryptResponse response = new DecryptResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }

}
