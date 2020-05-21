package com.example.adminserver.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * SecurityConfig 的配置类：<br/>
 * 配置了登录界面为 "/login.html", 退出界面为 "/logout"。<br/>
 * 这些页面以及静态资源css、img 都在引入的 jar中，这些资源的访问不需要认证。给这些资源加上 permitAll() 方法。<br/>
 * 除上述以外的资源访问需要权限认证，即加上 authenticated() 方法。<br/>
 * 另外，这些静态资源不支持 CSFR（跨域请求伪造），所以禁用掉 CSFR,最后需要开启 Http 的基本认证，即 httpBasic() 方法.
 *
 * @author Administrator
 * @date 2020/5/21 下午 10:07
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");

        httpSecurity.authorizeRequests()
                .antMatchers(adminContextPath + "/asserts/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()
                .csrf().disable();
        // @formatter:on
    }
}
