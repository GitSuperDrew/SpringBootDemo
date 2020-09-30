package com.study.module.jooq.service;

import com.study.module.jooq.tables.pojos.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/9/30 下午 10:41
 */
public interface UserService {
    void delete(long id);

    void insert(User user);

    int update(User user);

    User selectById(long id);

    List<User> selectAll(int pageNum, int pageSize);
}
