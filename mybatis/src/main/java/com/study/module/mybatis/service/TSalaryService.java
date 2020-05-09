package com.study.module.mybatis.service;

import com.study.module.mybatis.entity.TSalary;

import java.util.List;

/**
 * 薪资表(TSalary)表服务接口
 *
 * @author makejava
 * @since 2020-04-25 22:08:25
 */
public interface TSalaryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TSalary queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TSalary> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tSalary 实例对象
     * @return 实例对象
     */
    TSalary insert(TSalary tSalary);

    /**
     * 修改数据
     *
     * @param tSalary 实例对象
     * @return 实例对象
     */
    TSalary update(TSalary tSalary);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}