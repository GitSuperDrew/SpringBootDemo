package com.study.module.mybatisplus.controller;

import com.study.module.mybatisplus.entity.User;
import com.study.module.mybatisplus.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @date 2020/5/8 下午 8:18
 */
@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class RedisController {
    /**
     * redis中存储的过期时间60s
     */
    private static int ExpireTime = 60;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 对redis设值：String类型的key插入到redis
     * 注意，下面方法没有对参数指定【@RequestParam(value = "key") String key】所以，URL传递过来 的参数必须和此处的参数值保持一致
     *
     * @param key   redis-key
     * @param value redis-value
     * @return true:插入成功，false：失败
     */
    @RequestMapping(value = "set")
    public boolean redisSet(String key, String value) {
        User user = new User();
        user.setId(12);
        user.setAge(23);
        user.setName("Drew");
        user.setEmail("18799730133@139.com");
        redisUtil.set("user1", user.toString(), 60);
        return redisUtil.set(key, value);
    }

    @RequestMapping("get")
    public Object redisGet(String key) {
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key) {
        return redisUtil.expire(key, ExpireTime);
    }

    // TODO 测试说明：此时之前，请将windows下的redis服务开启（说明：如果配置了redis在window中的环境变量Path,那么可以直接[Win+R]输入【redis-server.exe >>
    //  ENTER】将redis服务开启，同样【redis-cli.exe >> ENTER】）
    // 第一步：请求 http://localhost:8989/redis/set?key=one&value=Drew （此时的 key 有效期为永久，当然如果redis出现异常，也是会导致此key为 one 过期）
    // 第二步：请求 http://localhost:8989/redis/get?key=one
    // 第三步：请求 http://localhost:8989/redis/expire?key=one  (将 key为one 设置有效期为60秒)
}
