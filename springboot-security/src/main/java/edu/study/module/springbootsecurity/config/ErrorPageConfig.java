package edu.study.module.springbootsecurity.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author zl
 * @date 2021/1/23 11:31
 **/
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        //权限不足的页面导向
        ErrorPage error403 = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
        registry.addErrorPages(error403);
    }
}
