package com.example.eurekaclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 构建 RESTful API，它支持 XML和 JSON数据格式，默认实现了序列化，可以自动将 JSON 字符串转换为实体。
 *
 * @author Administrator
 * @date 2020/5/19 上午 9:09
 */
@RestController
@RequestMapping(value = "rest")
public class RestTestController {

    /**
     * URL: http://localhost:8762/rest/testRest
     *
     * @return
     */
    @GetMapping("/testRest")
    public String testRest() {
        RestTemplate restTemplate = new RestTemplate();
        // 请求访问一个远程调用的结果。（启示：User user = restTemplate.getForObject("http://www.****.com/", User.class);）
        return restTemplate.getForObject("http://www.baidu.com/", String.class);
    }
}
