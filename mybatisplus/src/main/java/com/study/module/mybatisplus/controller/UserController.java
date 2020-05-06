package com.study.module.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.module.mybatisplus.entity.User;
import com.study.module.mybatisplus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    // ===========================================================
    // ===================👇👇👇 查询操作 👇👇👇======================
    // ===========================================================

    /**
     * URL：http://localhost:8989/mybatisplus/user/findAll
     *
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<User> findAll() {
        List<User> users = iUserService.list();
        return users;
    }

    /**
     * URL：http://localhost:8989/mybatisplus/user/findById/3
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id) {
        User user = iUserService.getById(id);
        return user;
    }

    /**
     * 批量查询
     * URL：http://localhost:8989/mybatisplus/user/findByIds?ids=6,7,8
     *
     * @param ids 用户ID集合
     * @return 批量的用户信息
     */
    @RequestMapping(value = "/findByIds", method = RequestMethod.GET)
    public Collection<User> findByIds(@RequestParam(value = "ids", required = false) Integer[] ids) {
        Integer array[] = {1, 2, 3};
        List<Integer> tmpList = Arrays.asList((ids == null || ids.length == 0) ? array : ids);
        return iUserService.listByIds(tmpList);
    }

    /**
     * 使用条件map，综合查询。
     * URL: http://localhost:8989/mybatisplus/user/findByNameEq?name=Drew
     *
     * @param username 用户姓名
     * @return
     */
    @RequestMapping(value = "/findByNameEq", method = RequestMethod.GET)
    public Collection<User> findByNameEq(@RequestParam(value = "name") String username) {
        Map<String, Object> columnMap = new HashMap<>(5);
        // key为对应的表列名称(同样是精确查找，只不过前端看可以封装成一个对象用JSON传递过来并在这里转成Map对象)
        columnMap.put("name", username);
        return iUserService.listByMap(columnMap);
    }

    /**
     * 范围查询
     * URL：http://localhost:8989/mybatisplus/user/findByRangeId?min=3&max=10
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    @RequestMapping(value = "/findByRangeId", method = RequestMethod.GET)
    public List<User> findByRangeId(@RequestParam(value = "min") int min,
                                    @RequestParam(value = "max") int max) {
        QueryWrapper queryWrapper = new QueryWrapper<>().between("id", min, max);
        return iUserService.list(queryWrapper);
    }


    // ===========================================================
    // ===================👇👇👇 插入操作 👇👇👇======================
    // ===========================================================


    // ===========================================================
    // ===================👇👇👇 删除操作 👇👇👇======================
    // ===========================================================

}
