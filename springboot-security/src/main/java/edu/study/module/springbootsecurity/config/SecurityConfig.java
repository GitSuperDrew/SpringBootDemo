package edu.study.module.springbootsecurity.config;

import edu.study.module.springbootsecurity.service.impl.MyUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author zl
 * @date 2021/1/23 11:34
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    MyUserDetailsService myUserDetailsService;

    /**
     * http安全配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求拦截
        http.authorizeRequests()
                //放在所有拦截的前面放行不需要拦截的资源
                .antMatchers("/static/**").permitAll()
                //放行登录
                .antMatchers("/login", "/index","/logining").permitAll()
                //放行注销
                .antMatchers("/logout").permitAll()
                //放行注册
                .antMatchers("/reg", "/register").permitAll()
                //此页需要权限
                .antMatchers("/main").hasAnyAuthority("ROLE_PRODUCT_LIST")
                //除上所有拦截需要用户认证
                .anyRequest().authenticated()
                .and()
                //for login认证
                .formLogin().loginPage("/login")
                //登陆错误页
                .failureUrl("/login?error=true")
                .and()
                //关闭csrf校验
                .csrf().disable();

    }

    /**
     * 认证管理器配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                //查询时需要密码加密后和数据库做比较
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
