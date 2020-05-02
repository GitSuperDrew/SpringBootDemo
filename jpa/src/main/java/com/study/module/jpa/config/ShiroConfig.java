package com.study.module.jpa.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 *
 * @author Administrator
 */
@Configuration
public class ShiroConfig {
    /**
     * 1.创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 添加shiro内置过滤器
         * 常用的过滤器：
         *  anon: 无需认证（登录可以访问）
         *  authc: 必须认证才可以访问
         *  user: 如果使用 rememberMe 的功能可以直接昂问
         *  perms: 该资源必须得到资源权限才可以访问
         *  role: 该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 第一种写法：具体验证授权访问的定义
        /* http://localhost:8888/student/stu_module-add
         * http://localhost:8888/student/stu_module-update
         * 被拦截到登录页面中，
         * http://localhost:8888/student/stu_module-list
         * 不被拦截。
         * */
        filterMap.put("/student/stu_module-add", "authc");
        filterMap.put("/student/stu_module-update", "authc");
        filterMap.put("/student/stu_module-list", "anon");
        // 第二种方法：通配符（指定模块下都可以访问）  如下：学生模块下的所有功能都可以在无登录下登录。
        // filterMap.put("/student/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        // 【被拦截后】的页面指定跳转到的页面：
        // 修改登录页面(访问的controller，此处我写在了 StudentController中，得到登录访问连接为 http://localhost:8888/student/login)
        shiroFilterFactoryBean.setLoginUrl("/student/toLogin");
        return shiroFilterFactoryBean;
    }

    /**
     * 2.创建 DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("studentRealm") StudentRealm studentRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(studentRealm);
        return securityManager;
    }

    /**
     * 3.创建Realm
     */
    @Bean(name = "studentRealm")
    public StudentRealm getRealm() {
        return new StudentRealm();
    }
}
