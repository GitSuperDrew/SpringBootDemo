package com.study.module.mybatis.controller;

import com.study.module.mybatis.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用户表（用来测试代码生成插件EasyCode）(User)表控制层
 * <pre>
 *     swagger2接入SpringBoot的学习示例网址：https://www.jb51.net/article/143533.htm
 *      1. 此controller访问的地址：例如 http://localhost:8089/users
 *      2. 当前Controller的swaggerUI2文档：http://localhost:8089/swagger-ui.html#/
 * <pre/>
 * @author Drew
 * @since 2020-04-19 09:00:45
 */
@Api(tags = "2.用户信息CURD操作", value = "")
@RestController
@RequestMapping(value = "/users")
public class UserDetailController {
    /**
     * 数据准备
     */
    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>());

    static {
        users.put(1, new User(1, "drew1", "drew1"));
        users.put(2, new User(2, "drew2", "drew2"));
        users.put(3, new User(3, "drew3", "drew3"));
        users.put(4, new User(4, "drew4", "drew4"));
        users.put(5, new User(5, "drew5", "drew5"));
        users.put(6, new User(6, "drew6", "drew6"));
    }

    @ApiOperation(value = "获取用户列表", notes = "获取所有的用户信息")
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", defaultValue = "3", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Integer id) {
        return users.get(id);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Integer id, @RequestBody User user) {
        User u = new User(id, user.getName(), user.getPassword());
        System.out.println("【更新前】所有的用户信息：\n" + users);
        users.put(id, u);
        System.out.println("【更新后】所有的用户信息：\n" + users);
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", defaultValue = "1", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Integer id) {
        System.out.println("【删除前】所有的用户信息：\n" + users);
        users.remove(id);
        System.out.println("【删除后】所有的用户信息：\n" + users);
        return "success";
    }
}
