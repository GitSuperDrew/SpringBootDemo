package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Administrator
 * @date 2020/5/15 上午 9:40
 */
@Api(tags = "测试 Thymeleaf 是否成功引入到 Spring Boot中")
@Controller
@RequestMapping(value = "/demo")
public class DemoController {

    /**
     * URL: http://localhost:8080/demo/index
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "访问录入", notes = "请求地址为： http://localhost:8080/demo/index")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("name", "Drew");
        return "index";
    }
}
