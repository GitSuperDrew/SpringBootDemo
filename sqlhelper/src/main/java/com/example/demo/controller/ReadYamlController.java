package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 2:39
 */
@RequestMapping(value = "/readYamlInfo")
@RestController
public class ReadYamlController {
    @Value("${spring.datasource.username}")
    private String dataSourceName;
    @Value("${spring.datasource.password}")
    private String dataSourcePwd;

    /**
     * 读取登录的配置信息
     * URL: http://localhost:8080/readYamlInfo/showLoginInfo
     *
     * @return
     */
    @RequestMapping("/showLoginInfo")
    public String showLoginInfo() {
        return dataSourceName + ":" + dataSourcePwd;
    }
}
