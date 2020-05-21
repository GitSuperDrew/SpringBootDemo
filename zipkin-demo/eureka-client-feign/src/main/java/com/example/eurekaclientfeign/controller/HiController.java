package com.example.eurekaclientfeign.controller;

import com.example.eurekaclientfeign.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/5/21 上午 10:34
 */
@RestController
public class HiController {

    @Autowired
    HiService hiService;

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam(value = "name", defaultValue = "DREW", required = false) String name) {
        return hiService.sayHi(name);
    }
}
