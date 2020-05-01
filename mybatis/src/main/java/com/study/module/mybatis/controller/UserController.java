package com.study.module.mybatis.controller;

import com.study.module.mybatis.entity.User;
import com.study.module.mybatis.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表（用来测试代码生成插件EasyCode）(User)表控制层
 * <pre>
 *     swagger2接入SpringBoot的学习示例网址：https://www.jb51.net/article/143533.htm
 *      1. 此controller访问的地址：例如 http://localhost:8089/user/selectAll
 *      2. 当前Controller的swaggerUI2文档：http://localhost:8089/swagger-ui.html#/
 * <pre/>
 * @author Drew
 * @since 2020-04-11 22:48:30
 */
@Api(tags = "1.用户信息CURD操作", value = "")
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据 {`@ApiIgnore` 使用该注解忽略这个API【即：在 swagger UI 文档中不会显示此接口】}
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiIgnore
    @GetMapping("selectOne/{id}")
    public User selectOne(@PathVariable("id") Integer id) {
        return this.userService.queryById(id);
    }

    @ApiOperation(value = "获取所有用户", notes = "get all user limit 10")
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<User> selectAll() {
        return this.userService.queryAllByLimit(0, 10);
    }

    @ApiOperation(value = "根据用户ID查询用户信息", notes = "get user by ID")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer", paramType = "path",
            defaultValue = "1")
    @RequestMapping(value = "selectById/{id}", method = RequestMethod.GET)
    public User selectById(@PathVariable("id") Integer id) {
        return this.userService.queryById(id);
    }

    /**
     * ====================== >>>> 更行指定用户的信息
     *
     * @return 所有的用户信息
     */
    private Map<Integer, User> userData() {
        Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>(100));
        List<User> userList = this.userService.queryAllByLimit(0, 100);
        for (User user : userList) {
            users.put(user.getId(), user);
        }
        return users;
    }

    @ApiOperation(value = "更新指定用户ID信息", notes = "update user info by id.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable Integer id, @RequestBody User user) {
        Map<Integer, User> users = this.userData();
        System.out.println("【更新前】所有的用户信息：\n" + users);
        User user1 = users.get(id);
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        users.put(id, user1);
        System.out.println("【更新后】所有的用户信息：\n" + users);
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的ID，来删除指定的用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, defaultValue = "2", dataType = "Integer",
            paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable(value = "id") Integer id) {
        Map<Integer, User> users = this.userData();
        System.out.println("【删除前】所有的用户信息：\n" + users);
        users.remove(id);
        System.out.println("【删除后】所有的用户信息：\n" + users);
        return "success";
    }

    @ApiIgnore
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }

}