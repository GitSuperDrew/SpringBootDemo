package com.study.module.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Drew
 */
@SpringBootApplication
@MapperScan(value = "com.study.module.mybatisplus.mapper")
public class MybatisplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
        System.out.println(" ▄▀▀▄ ▄▀▄  ▄▀▀▄ ▀▀▄  ▄▀▀█▄▄   ▄▀▀█▄   ▄▀▀▀█▀▀▄  ▄▀▀█▀▄   ▄▀▀▀▀▄              ▄▀▀█▄▄   ▄▀▀▄▀▀▀▄  ▄▀▀█▄▄▄▄  ▄▀▀▄    ▄▀▀▄ \n" +
                "█  █ ▀  █ █   ▀▄ ▄▀ ▐ ▄▀   █ ▐ ▄▀ ▀▄ █    █  ▐ █   █  █ █ █   ▐             █ ▄▀   █ █   █   █ ▐  ▄▀   ▐ █   █    ▐  █ \n" +
                "▐  █    █ ▐     █     █▄▄▄▀    █▄▄▄█ ▐   █     ▐   █  ▐    ▀▄               ▐ █    █ ▐  █▀▀█▀    █▄▄▄▄▄  ▐  █        █ \n" +
                "  █    █        █     █   █   ▄▀   █    █          █    ▀▄   █                █    █  ▄▀    █    █    ▌    █   ▄    █  \n" +
                "▄▀   ▄▀       ▄▀     ▄▀▄▄▄▀  █   ▄▀   ▄▀        ▄▀▀▀▀▀▄  █▀▀▀                ▄▀▄▄▄▄▀ █     █    ▄▀▄▄▄▄      ▀▄▀ ▀▄ ▄▀  \n" +
                "█    █        █     █    ▐   ▐   ▐   █         █       █ ▐                  █     ▐  ▐     ▐    █    ▐            ▀    \n" +
                "▐    ▐        ▐     ▐                ▐         ▐       ▐                    ▐                   ▐                      ");
    }

}
