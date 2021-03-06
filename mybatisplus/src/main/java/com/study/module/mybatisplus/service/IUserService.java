package com.study.module.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.module.mybatisplus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Drew
 * @since 2020-05-05
 */
public interface IUserService extends IService<User> {

    public IPage<User> selectUserPage(Page<User> page);

}
