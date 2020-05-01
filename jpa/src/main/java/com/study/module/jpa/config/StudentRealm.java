package com.study.module.jpa.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 *
 * @author Administrator
 */
public class StudentRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权逻辑");
        return null;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证逻辑");
        // 假设 数据库的用户名和密码
        String username = "dd";
        String password = "dd";
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 1.判断用户名
        if (!token.getUsername().equals(username)) {
            //用户名不存在， shiro 底层抛出  UnknowAccountException
            return null;
        }
        // 2.判断密码
        return new SimpleAuthenticationInfo("", password, "");
    }
}
