package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-05-16 19:33:47
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Long id) {
        return this.userService.queryById(id);
    }

//    /**
//     * <pre>
//     * URL：http://localhost:8080/user/_useMyBatis
//     *
//     * <pre/>
//     * @param pageNo
//     * @param pageSize
//     * @param sort
//     * @return
//     */
//    @GetMapping("/_useMyBatis")
//    public PagingResult list_useMyBatis(@RequestParam(name = "pageNo", required = false) Integer pageNo,
//                                        @RequestParam(name = "pageSize", required = false) Integer pageSize,
//                                        @RequestParam(name = "sort", required = false) String sort) {
//        User queryCondition = new User();
//        queryCondition.setAge(10);
//
//        PagingRequest request = SqlPaginations.preparePagination(pageNo == null ? 1 : pageNo, pageSize == null ? -1 : pageSize, sort);
//
//        List<User> users = userService.selectAll(queryCondition);
//        String json = JSONBuilderProvider.simplest().toJson(request.getResult());
//        System.out.println(json);
//        json = JSONBuilderProvider.simplest().toJson(users);
//        System.out.println(json);
//        return request.getResult();
//    }

}