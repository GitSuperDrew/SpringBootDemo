package com.study.module.controller;

import com.study.module.config.MyInfoConfigBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 3:03
 */
@Api(tags = "利用实体类接收，批量读取配置信息")
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
    @ApiOperation(value = "利用实体类接收，批量读取配置信息", notes = "利用实体类接收，批量读取配置信息")
    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    public String myInfo() {
        return myInfoConfigBean.getGreeting() + "-" + myInfoConfigBean.getName() + "-" + myInfoConfigBean.getUuid() + "-" + myInfoConfigBean.getMax();
    }
}
