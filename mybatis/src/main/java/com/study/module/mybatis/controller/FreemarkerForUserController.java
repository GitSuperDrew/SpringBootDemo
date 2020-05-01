package com.study.module.mybatis.controller;

import com.google.common.collect.ImmutableMap;
import com.study.module.mybatis.entity.User;
import com.study.module.mybatis.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/freemarkerForUser")
public class FreemarkerForUserController {

    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 前端直接发送请求：http://localhost:8089/freemarkerForUser/users1 ；之后页面自加载的时候通过ajax请求数据接口
     *
     * @param model 可理解为：携带数据的对象容器
     * @return 跳转到指定页面
     */
    @RequestMapping(value = "/users1")
    public String findUsers1(Model model) {
        List<User> users = userService.queryAllByLimit(0, 100);
        // 1.添加需传送数据
        model.addAttribute("users", users);
        return "user/freemarkerUsers1";
    }

    // ================================== 👇👇👇 ====================================

    /**
     * 前端直接发送请求：http://localhost:8089/freemarkerForUser/users2 ；之后页面自加载的时候通过ajax请求数据接口
     *
     * @return 指定的页面名称
     */
    @RequestMapping(value = "/users2")
    public String findUsers2() {
        return "user/freemarkerUsers2";
    }

    @ResponseBody
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Map<String,Object> findAll() {
        List<User> users = userService.queryAllByLimit(0, 13);
        return ImmutableMap.of("data", users);
    }
    // ================================== 👆👆👆 ====================================

    /**
     * 请求的路径为: http://localhost:8089/freemarkerForUser/demo
     *
     * @param model 可理解为：携带数据的对象容器
     * @return 跳转到指定页面
     */
    @RequestMapping("/demo")
    public String index(Model model) {
        // 1.添加需传送数据
        model.addAttribute("msg", "Welcome, Drew.");
        // 2.返回到 resources/templates/freemarkerDemo.ftl页面。注意：必须以"/"开头，如写成“demo/freemarkerDemo”则是错误的！
        return "/demo/freemarkerDemo";
    }
}
