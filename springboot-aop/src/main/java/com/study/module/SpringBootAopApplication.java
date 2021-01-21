package com.study.module;

import com.study.module.filters.RequestFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author zhongzero
 */
@SpringBootApplication
@MapperScan(value = "com.study.module.dao")
public class SpringBootAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAopApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean registrationBean() {
        // 将自定以的过滤器注册到过滤器Bean中
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RequestFilter());
        // 过滤所有的请求URL
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
