package com.example.servicehi.service.impl;

import com.example.servicehi.dao.UserDao;
import com.example.servicehi.entity.User;
import com.example.servicehi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2020/5/22 下午 7:54
 */
@Service
public class UserServiceImpl implements UserService {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    @Override
    public User create(User user) {
        String hash = ENCODER.encode(user.getPassword());
        user.setPassword(hash);
        User u = userDao.save(user);
        return u;
    }
}
