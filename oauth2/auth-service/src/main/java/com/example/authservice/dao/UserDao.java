package com.example.authservice.dao;

import com.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @date 2020/5/22 下午 4:18
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
