package com.study.module.jpa.dao;

import com.study.module.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
