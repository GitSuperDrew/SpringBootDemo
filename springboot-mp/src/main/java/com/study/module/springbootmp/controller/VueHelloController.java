package com.study.module.springbootmp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zl
 * @date 2021/1/19 11:56
 **/
@Controller
@RequestMapping(value = "/vue")
public class VueHelloController {

    @GetMapping(value = "/hello")
    public ModelAndView sayHello(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/vue/vue-axios-secret-aes.html");
        return mv;
    }

    @GetMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", "0001");
        mv.addObject("name", "DrewBob");
        mv.addObject("age", "32");
        mv.setViewName("/index.html");
        return mv;
    }
}
