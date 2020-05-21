package com.example.eurekaclient.controller;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/21 下午 3:11
 */
@RestController
public class HiController {
    @Autowired
    Tracer tracer;

    @Value("${server.port}")
    String port;

    @GetMapping(value = "/hi")
    public String home(@RequestParam String name) {
        tracer.currentSpan().tag("name", "DREW");
        return "Hi, " + name + ", I'm from port: " + port;
    }
}
