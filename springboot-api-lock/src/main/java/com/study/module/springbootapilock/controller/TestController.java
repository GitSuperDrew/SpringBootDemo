package com.study.module.springbootapilock.controller;

import com.study.module.springbootapilock.annotation.RateLimiter;
import com.study.module.springbootapilock.enums.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类：演示请求锁
 *
 * @author zl
 * @create 2022-06-11 9:58
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    /**
     * 演示：每一个 IP 地址，在 5 秒内只能访问 3 次。
     */
    @GetMapping("/hi")
    @RateLimiter(time = 5, count = 3, limitType = LimitType.IP)
    public String hi() {
        return "hi";
    }
}
