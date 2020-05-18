package com.example.demo.controller;

import com.example.demo.config.MyInfoConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 3:03
 */
@RequestMapping(value = "/showInfo")
@RestController
@EnableConfigurationProperties({MyInfoConfigBean.class})
public class MyInfoController {
    @Autowired
    MyInfoConfigBean myInfoConfigBean;

    /**
     * 利用实体类接收，批量读取配置信息
     * URL: http://localhost:8080/showInfo/myInfo
     *
     * @return
     */
    @RequestMapping(value = "/myInfo")
    public String myInfo() {
        return myInfoConfigBean.getGreeting() + "-" + myInfoConfigBean.getName() + "-" + myInfoConfigBean.getUuid() + "-" + myInfoConfigBean.getMax();
    }
}
