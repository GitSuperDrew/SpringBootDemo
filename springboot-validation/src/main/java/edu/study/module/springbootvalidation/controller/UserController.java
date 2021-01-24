package edu.study.module.springbootvalidation.controller;

import edu.study.module.springbootvalidation.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户测试参数验证的控制类
 *
 * @author zl
 * @date 2021/1/24 10:42
 **/
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @PostMapping(path = "/add")
    public Object addUser(@RequestBody @Valid UserVO userVO){
        System.out.println("传入的参数为："  + userVO);
        return "^_^ 参数验证成功！";
    }
}
