package com.jiangfeixiang.mpdemo.springbootmp.util.xss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class XssFilterConfig {

    @Value("${xss.enabled:true}")
    private String enabled;

    @Value("${xss.excludes:}")
    private String excludes;

    @Value("${xss.includes:}")
    private String includes;

    @Value("${xss.urlPatterns:/*}")
    private String urlPatterns;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(urlPatterns.split(","));
        registration.setName("XssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<>(3);
        initParameters.put("excludes", excludes);
        initParameters.put("includes", includes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }
}
