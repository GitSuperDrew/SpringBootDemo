package com.example.eurekafeignclient.service;

import com.example.eurekafeignclient.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 * @date 2020/5/19 下午 5:29
 */
@FeignClient(value = "eureka-client", configuration = FeignConfig.class, fallback = HiHystrix.class)
public interface EurekaClientFeign {
    @GetMapping("/hi/hi")
    String sayHiFormClientEureka(@RequestParam(value = "name") String name);
}
