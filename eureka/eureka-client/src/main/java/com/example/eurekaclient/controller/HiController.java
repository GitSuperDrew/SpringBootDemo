package com.example.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/18 下午 9:23
 */
@RestController
@RequestMapping(value = "/hi")
public class HiController {
    @Value(value = "${server.port}")  // 从配置文件中读取配置的端口信息
            String port;

    /**
     * URL：http://localhost:8762/hi/hi?name=drew
     *
     * @param name
     * @return "hi drew, I am from port:8762"
     */
    @GetMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi " + name + ", I am from port:" + port;
    }
}
