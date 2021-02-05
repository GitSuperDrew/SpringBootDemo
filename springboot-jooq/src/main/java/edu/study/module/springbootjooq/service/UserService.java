package edu.study.module.springbootjooq.service;

import edu.study.module.springbootjooq.generate.tables.pojos.User;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/10/1 上午 7:48
 */
public interface UserService {
    void delete(long id);

    void insert(User user);

    int update(User user);

    /**
     * 获取单个详情
     *
     * @param id 用户ID
     * @return
     */
    User selectById(long id);

    List<User> selectAll(int pageNum, int pageSize);
}
