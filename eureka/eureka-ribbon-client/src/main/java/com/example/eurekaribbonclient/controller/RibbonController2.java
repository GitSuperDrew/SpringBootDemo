package com.example.eurekaribbonclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/19 下午 5:08
 */
@RequestMapping(value = "/ribbon2")
@RestController
public class RibbonController2 {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * URL：http://localhost:8769/ribbon2/testRibbon
     *
     * @return 多次请求此链接，可以发现两个URL的值任意切换（example.com  和 google.com 任意切换）
     */
    @GetMapping("testRibbon")
    public String testRibbon() {
        ServiceInstance instance = loadBalancerClient.choose("stores");
        return instance.getHost() + ":" + instance.getPort();
    }
}
