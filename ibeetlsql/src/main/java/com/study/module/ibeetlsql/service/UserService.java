package com.study.module.ibeetlsql.service;

import com.study.module.ibeetlsql.entity.User;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/5/5 上午 10:41
 */
public interface UserService {
    /**
     * 根据姓名，查询用户信息
     *
     * @param name 用户姓名
     * @return 用户信息
     */
    User selectByName(String name);

    List<User> selectByNameLike(String name);

    User findById(Integer id);

    /**
     * 得到所有的用户信息
     *
     * @return 所有用户信息
     */
    List<User> selectAll();

    List<User> findAll();

    /**
     * 根据ID，得到用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User selectById(Integer id);

    List<User> findByNameLike(String name);

    User findById_Origin(Integer id);

    /**
     * 根据用户名称模糊查询
     *
     * @param name 用户名称
     * @return 匹配上的所有用户信息
     */
    List<User> queryFindByNameLike(String name);
}
