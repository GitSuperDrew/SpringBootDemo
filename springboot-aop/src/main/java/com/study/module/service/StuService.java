package com.study.module.service;

import com.study.module.entity.Stu;

import java.util.List;

/**
 * (Stu)表服务接口
 *
 * @author drew
 * @since 2021-01-04 16:26:18
 */
public interface StuService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Stu queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Stu> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param stu 实例对象
     * @return 实例对象
     */
    Stu insert(Stu stu);

    /**
     * 修改数据
     *
     * @param stu 实例对象
     * @return 实例对象
     */
    Stu update(Stu stu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}