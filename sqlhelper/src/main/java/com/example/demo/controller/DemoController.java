package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Administrator
 * @date 2020/5/15 上午 9:40
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoController {

    /**
     * URL: http://localhost:8080/demo/index
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("name","Drew");
        return "index";
    }
}
