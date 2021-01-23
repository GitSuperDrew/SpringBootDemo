package edu.study.module.springbootsecurity.service.impl;

import edu.study.module.springbootsecurity.dao.SysUserDao;
import edu.study.module.springbootsecurity.entity.SysPermission;
import edu.study.module.springbootsecurity.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zl
 * @date 2021/1/23 11:51
 **/
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询当前用户信息
        SysUser user = this.sysUserDao.selectByUserName(username);
        //查询当前用户权限
        List<SysPermission> list = this.sysUserDao.findPermissionByUserName(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        /*
         * 将getPermTag()构建一个GrantedAuthority接口实例对象，放于List<GrantedAuthority>中
         * */
        list.forEach(l -> {
            authorities.add(new SimpleGrantedAuthority(l.getPermTag()));
        });
        //用户信息设置权限集合
        user.setAuthorities(authorities);
        return user;
    }
}
