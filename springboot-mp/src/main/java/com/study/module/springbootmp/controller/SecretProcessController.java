package com.study.module.springbootmp.controller;

import com.study.module.springbootmp.annotation.SIProtection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加密和解密测试控制类
 *
 * @author zl
 * @date 2021/1/18 19:16
 **/
@RestController
@RequestMapping(value = "/secret")
public class SecretProcessController {

    /**
     * 请求地址：http://localhost:8080/secret/getInfo?str=hello
     * @param str 输入参数（程序中不加密）
     * @return
     */
    @SIProtection
    @GetMapping(value = "/getInfo")
    public Object getInfo(String str) {
        System.out.println("输入的内容为：" + str);
        // 加密后的内容为：a346727cb21019989aca9bb2b771446fafa5fdb0c7fa45dbd6a7193789c48e8555b5f4d912ec56fe0b5a2820fb822b37
        return "hello world! hello secret data.";
    }

}
