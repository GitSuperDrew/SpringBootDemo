package com.study.module.mapstruct.config;

import com.study.module.mapstruct.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 学习网址：https://blog.csdn.net/csdnluolei/article/details/87696391
 * @author Administrator
 * @date 2020/7/26 下午 5:18
 */
@Configuration
public class LogConfig {
    private static final Logger LOG = LoggerFactory.getLogger(LogConfig.class);
    @Bean
    public Student logMethod() {
        LOG.info("==========print log==========");
        return new Student();
    }
}
