package edu.study.module.repeat_submit_intercept.test;

import edu.study.module.repeat_submit_intercept.entity.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务已启动，则执行此方法进行模拟多线程测试
 *
 * @author zl
 * @create 2021-04-02 17:46
 */
@Component
public class RunTest implements ApplicationRunner {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) {
        // 执行次数
        int execCount = 10;
        System.out.println("执行多线程测试 >>>>> RunTest.java");
        String url = "http://localhost:8080/noRepeat/submit";
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < execCount; i++) {
            Long userId = Long.parseLong(String.valueOf(1));
            String userName = "userName" + 1;
            User user = new User(userId, userName);
            HttpEntity request = buildRequest(user);
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + System.currentTimeMillis());
                    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                    System.out.println("Thread:" + Thread.currentThread().getName() + "," + response.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.countDown();
    }

    private HttpEntity buildRequest(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "yourToken");
        Map<String, Object> body = new HashMap<>(2);
        body.put("userId", user.getUserId());
        body.put("userName", user.getUserName());
        return new HttpEntity<>(body, headers);
    }

}

