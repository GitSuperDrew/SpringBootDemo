package edu.study.module.springbootquartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author drew
 * @date 2021/2/22 19:52
 **/
@Slf4j
@Service
public class TaskSchedulerService {

    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        this.scheduler = schedulerFactoryBean.getScheduler();
    }

    /**
     * 暂停任务
     */
    public void pauseTask(String name, String group) {
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("暂停任务失败：{}", e);
        }
    }

    /**
     * 恢复任务执行
     */
    public void resumeTask(String name, String group) {
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("恢复任务失败：{}", e);
        }
    }

}
