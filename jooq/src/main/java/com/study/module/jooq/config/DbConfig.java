package com.study.module.jooq.config;


import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Administrator
 * @date 2020/9/30 下午 11:44
 */
@Data
@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DbConfig {
    private String url;
    private String username;
    private String password;

    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
