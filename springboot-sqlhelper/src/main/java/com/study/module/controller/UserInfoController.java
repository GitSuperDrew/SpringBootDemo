package com.study.module.controller;

import com.study.module.config.UserConfigBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 3:26
 */
@Api(tags = "读取配置文件中的用户信息")
@RestController
@RequestMapping(value = "/test")
@EnableConfigurationProperties({UserConfigBean.class})
public class UserInfoController {
    @Autowired
    UserConfigBean userConfigBean;

    /**
     * URL: http://localhost:8080/test/userInfo
     *
     * @return
     */
    @ApiOperation(value = "提供配置文件中的用户信息", notes = "测试spring boot中提取配置文件信息", tags = "USER INFO")
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo() {
        return userConfigBean.getName() + "/" + userConfigBean.getAge();
    }
}
