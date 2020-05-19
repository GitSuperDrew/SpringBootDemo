package com.example.eurekafeignclient.controller;

import com.example.eurekafeignclient.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/19 下午 5:35
 */
@RequestMapping(value = "/feign")
@RestController
public class HiController {
    @Autowired
    private HiService hiService;

    /**
     * URL: http://localhost:8765/feign/hi
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam(value = "name", defaultValue = "[drew-feign-client]", required = false) String name) {
        return hiService.sayHi(name);
    }
}
