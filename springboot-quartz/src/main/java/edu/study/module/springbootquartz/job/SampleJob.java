package edu.study.module.springbootquartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author zl
 * @date 2021/2/22 19:54
 **/
@Slf4j
public class SampleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任务详情：" + jobExecutionContext.getJobDetail());
        log.info("这里是一个简单的任务") ;
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap() ;
        Set<Map.Entry<String, Object>> entryList = dataMap.entrySet() ;
        for (Map.Entry<String, Object> entry : entryList) {
            log.info("任务数据：key = {}, value = {}", entry.getKey(), entry.getValue());
        }

    }
}
