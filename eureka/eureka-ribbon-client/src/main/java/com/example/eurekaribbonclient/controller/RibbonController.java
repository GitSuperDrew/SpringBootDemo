package com.example.eurekaribbonclient.controller;

import com.example.eurekaribbonclient.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/19 下午 2:33
 */
@RequestMapping("/ribbon")
@RestController
public class RibbonController {
    @Autowired
    RibbonService ribbonService;

    /**
     * URL: http://localhost:8764/ribbon/hi?name=drew
     *
     * @param name
     * @return
     */
    @GetMapping("/hi")
    public String hi(@RequestParam(required = false, defaultValue = "drew") String name) {
        return ribbonService.hi(name);
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * URL: http://localhost:8764/ribbon/testRibbon
     *
     * @return
     */
    @GetMapping("/testRibbon")
    public String testRibbon() {
        ServiceInstance instance = loadBalancerClient.choose("eureka-client");
        return instance.getHost() + ":" + instance.getPort();
    }
}
