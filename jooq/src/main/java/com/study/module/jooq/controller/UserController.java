package com.study.module.jooq.controller;

import com.study.module.jooq.service.UserService;
import com.study.module.jooq.tables.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/9/30 下午 10:41
 */
@RestController
@RequestMapping(value = "/jooq/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable(value = "id") Long id) {
        return userService.selectById(id);
    }
}
