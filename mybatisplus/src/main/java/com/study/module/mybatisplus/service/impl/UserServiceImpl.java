package com.study.module.mybatisplus.service.impl;

import com.study.module.mybatisplus.entity.User;
import com.study.module.mybatisplus.mapper.UserMapper;
import com.study.module.mybatisplus.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Drew
 * @since 2020-05-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
