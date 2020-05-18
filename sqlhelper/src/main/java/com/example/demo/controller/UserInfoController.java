package com.example.demo.controller;

import com.example.demo.config.MyInfoConfigBean;
import com.example.demo.config.UserConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 3:26
 */
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
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo() {
        return userConfigBean.getName() + "/" + userConfigBean.getAge();
    }
}
