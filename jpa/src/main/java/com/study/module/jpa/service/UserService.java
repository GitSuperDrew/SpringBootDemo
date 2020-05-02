package com.study.module.jpa.service;

import com.study.module.jpa.entity.UserEntity;

import java.util.List;

/**
 * @author Administrator
 */
public interface UserService {

    List<UserEntity> findAll();

}
