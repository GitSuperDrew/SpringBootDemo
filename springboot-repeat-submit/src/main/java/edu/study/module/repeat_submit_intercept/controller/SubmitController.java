package edu.study.module.repeat_submit_intercept.controller;

import edu.study.module.repeat_submit_intercept.aop.NoRepeatSubmit;
import edu.study.module.repeat_submit_intercept.entity.User;
import edu.study.module.repeat_submit_intercept.utils.ApiResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author zl
 * @create 2021-04-02 15:44
 */
@RestController
@RequestMapping(path = "/noRepeat")
public class SubmitController {

    @Resource
    private RedisTemplate redisTemplate;


    @PostMapping("/submit")
    @NoRepeatSubmit(lockTime = 300)
    public Object submit(@RequestBody User userBean) {
        try {
            // 模拟业务场景
            Thread.sleep(500);
            System.out.println(userBean.toString());
            redisTemplate.opsForList().leftPush(UUID.randomUUID().toString().replaceAll("-", ""), userBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ApiResult(200, "成功", userBean.toString());
    }

}

