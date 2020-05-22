package com.example.servicehi.dao;

import com.example.servicehi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @date 2020/5/22 下午 7:53
 */
public interface UserDao extends JpaRepository<User, Long> {

}
