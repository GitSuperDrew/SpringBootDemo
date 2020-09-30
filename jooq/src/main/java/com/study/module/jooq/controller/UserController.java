package com.study.module.jooq.controller;

import com.study.module.jooq.service.UserService;
import com.study.module.jooq.tables.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/9/30 下午 10:41
 */
@RestController
@RequestMapping(value = "/jooq/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取指定用户详情
     *
     * @param id 用户ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable(value = "id") Long id) {
        return userService.selectById(id);
    }

    /**
     * 分页获取用户
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页码条目数
     * @return
     */
    @GetMapping(value = "/list")
    public List<User> selectAll(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return userService.selectAll(pageNum, pageSize);
    }
}
