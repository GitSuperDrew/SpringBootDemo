package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 2:39
 */
@Api(tags = "读取登录的配置信息")
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
    @ApiOperation(value = "读取登录的配置信息", notes = "读取登录的配置信息")
    @RequestMapping(value = "/showLoginInfo", method = RequestMethod.GET)
    public String showLoginInfo() {
        return dataSourceName + ":" + dataSourcePwd;
    }
}
