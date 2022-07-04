package com.jiangfeixiang.mpdemo.springbootmp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zl
 * @date 2022-07-04 17:53
 */
@RestController
@RequestMapping(value = "/test")
public class DemoTestController {

    /**
     * 测试 预防XSS攻击效果演示
     * <pre>
     *     http://localhost:8889/test/xss?htmlStr=<a href="www.baidu.com" onclick="alert(1);">baidu.com</a><script>alert(1);</script>
     *     http://localhost:8889/test/xss?htmlStr=<img width="200px" onerr="alert(1);"/>
     * </pre>
     *
     * @param htmlStr 带有脚本的字符串内容
     * @return 剔除脚本的字符
     */
    @GetMapping(value = "/xss")
    public String testXss(@RequestParam(value = "htmlStr") String htmlStr) {
        System.out.println("xss-html: \n" + htmlStr);
        return htmlStr;
    }
}
