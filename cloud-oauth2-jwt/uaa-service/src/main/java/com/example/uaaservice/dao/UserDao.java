package com.example.uaaservice.dao;

import com.example.uaaservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @date 2020/5/22 下午 10:23
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
