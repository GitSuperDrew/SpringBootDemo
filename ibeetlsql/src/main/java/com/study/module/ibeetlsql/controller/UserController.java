package com.study.module.ibeetlsql.controller;

import com.study.module.ibeetlsql.entity.User;
import com.study.module.ibeetlsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/5/5 上午 10:44
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    // =============================== 查询所有【无分页】==================================

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<User> selectAll() {
        return userService.selectAll();
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<User> findAll() {
        return userService.findAll();
    }

    // =============================== 根据用户名称查询（模糊/精确）【无分页】==================================

    @RequestMapping(value = "/selectByName", method = RequestMethod.GET)
    public User selectByName(@RequestParam(value = "name") String name) {
        return userService.selectByName(name);
    }

    @RequestMapping(value = "/selectByNameLike", method = RequestMethod.GET)
    public List<User> selectByNameLike(@RequestParam(value = "name") String name) {
        return userService.selectByNameLike(name);
    }


    @RequestMapping(value = "/queryFindByNameLike", method = RequestMethod.GET)
    public List<User> queryFindByNameLike(@RequestParam(value = "name") String name) {
        return userService.queryFindByNameLike(name);
    }

    @RequestMapping(value = "/findByNameLike", method = RequestMethod.GET)
    public List<User> findByNameLike(@RequestParam("name") String name) {
        return userService.findByNameLike(name);
    }

    // =============================== 根据 ID 查询 ==================================

    @RequestMapping(value = "/selectById/{id}", method = RequestMethod.GET)
    public User selectById(@PathVariable("id") Integer id) {
        return userService.selectById(id);
    }

    @RequestMapping(value = "/findById_Origin/{id}", method = RequestMethod.GET)
    public User findById_Origin(@PathVariable("id") Integer id) {
        return userService.findById_Origin(id);
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    // =============================== 根据 多条件 查询 ==================================

    // TODO 待续~~~~

}
