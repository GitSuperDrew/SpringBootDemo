package com.study.module.ibeetlsql.controller;

import com.study.module.ibeetlsql.entity.User;
import com.study.module.ibeetlsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/5/5 下午 5:05
 */
@Controller
@RequestMapping(value = "/test")
public class HelloController {

    @Autowired
    private UserService userService;

    /**
     * 测试视图解析器
     */
    @RequestMapping("/hello")
    public String hello(Model model) {
        String name = "YanQian";
        model.addAttribute("name", name);
        return "hello";
    }

    /**
     * URL：http://localhost:8787/test/findAll
     *
     * @param model
     * @return
     */
    @RequestMapping("findAll")
    public String findAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "/user/list";
    }

    /**
     * URL：http://localhost:8787/test/getOne/3
     *
     * @param id    用户ID
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOne/{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/user/edit";
    }
}
