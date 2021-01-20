package com.study.module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhongzero
 */
@SpringBootApplication
@MapperScan(value = "com.study.module.dao")
public class SpringBootAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAopApplication.class, args);
    }

}
