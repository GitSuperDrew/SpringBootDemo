package com.study.module.springbooteasyexcel.service;

import com.study.module.springbooteasyexcel.entity.Poet;

import java.util.List;

/**
 * (Poet)表服务接口
 *
 * @author makejava
 * @since 2020-10-11 14:17:34
 */
public interface PoetService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Poet queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Poet> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param poet 实例对象
     * @return 实例对象
     */
    Poet insert(Poet poet);

    /**
     * 修改数据
     *
     * @param poet 实例对象
     * @return 实例对象
     */
    Poet update(Poet poet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
