package edu.study.module.springbootactuator.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @date 2020/5/26 下午 7:35
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello World!";
    }
}
