package com.study.module.springbootswagger3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * Spring Boot 整合 Swagger3
 *
 * @author Administrator
 */
@EnableOpenApi
@MapperScan(basePackages = {"com.study.module.springbootswagger3.dao"})
@SpringBootApplication
public class Springbootswagger3Application {

    public static void main(String[] args) {
        SpringApplication.run(Springbootswagger3Application.class, args);
    }

}
