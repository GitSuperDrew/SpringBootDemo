package com.example.eurekafeignclient.service;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2020/5/19 下午 7:42
 */
@Component
public class HiHystrix implements EurekaClientFeign{

    @Override
    public String sayHiFormClientEureka(String name) {
        return "hi, " + name + ", 【sorry, error!】 =====> eureka-feign-client > Hystrix。";
    }
}
