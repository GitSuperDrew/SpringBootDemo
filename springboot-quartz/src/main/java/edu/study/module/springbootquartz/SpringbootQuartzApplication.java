package edu.study.module.springbootquartz;

import edu.study.module.springbootquartz.job.SampleJob;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author drew
 */
@SpringBootApplication
public class SpringbootQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootQuartzApplication.class, args);
    }

    @Bean
    public JobDetail sampleJobDetail() {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("k1", "v1");
        return JobBuilder.newJob(SampleJob.class).withIdentity("sampleJob")
                .usingJobData("key", "pack").usingJobData(dataMap).storeDurably().build();
    }

    @Bean
    public Trigger cronJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail())
                .withIdentity("t1", "CRON")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
