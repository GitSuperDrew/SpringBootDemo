package com.study.module.mybatisplus.controller;


import com.study.module.mybatisplus.entity.User;
import com.study.module.mybatisplus.service.IUserService;
import com.study.module.mybatisplus.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Drew
 * @since 2020-05-05
 */
@RestController
@RequestMapping("/mybatisplus/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * URL：http://localhost:8989/mybatisplus/user/findAll
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<User> findAll() {
        List<User> users = iUserService.list();
        return users;
    }

    /**
     * URL：http://localhost:8989/mybatisplus/user/findById/3
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id) {
        User user = iUserService.getById(id);
        return user;
    }

}
