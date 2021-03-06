package com.jiangfeixiang.mpdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zero
 * 说明：MapperScan中的路径：右击包选择copy reference
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.jiangfeixiang.mpdemo.springbootmp.mapper")
public class MpdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpdemoApplication.class, args);
    }

}
