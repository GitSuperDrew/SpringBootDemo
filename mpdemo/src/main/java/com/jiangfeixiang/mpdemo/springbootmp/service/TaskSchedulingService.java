package com.jiangfeixiang.mpdemo.springbootmp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @date 2020/8/21 下午 6:19
 */
@Slf4j
@Component
public class TaskSchedulingService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void myTask() throws InterruptedException {
        log.info("执行时间:{}", sdf.format(new Date()));
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void task2() {
        log.info("task2:{} - {}", "五秒执行一次", System.currentTimeMillis());
    }

}
