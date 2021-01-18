package com.study.module.springbootmp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.module.springbootmp.dao.UserDao;
import com.study.module.springbootmp.entity.User;
import com.study.module.springbootmp.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-01-16 13:42:51
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


}