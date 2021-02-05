package edu.study.module.springbootjooq.controller;

import edu.study.module.springbootjooq.generate.tables.pojos.User;
import edu.study.module.springbootjooq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/10/1 上午 7:47
 */
@RestController
@RequestMapping(value = "/springbootjooq/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public User findById(@PathVariable(value = "id") long id) {
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
                                @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize) {
        return userService.selectAll(pageNum, pageSize);
    }

}
