package com.study.module.mybatisoracle.service;

import com.study.module.mybatisoracle.entity.Salary;
import java.util.List;

/**
 * 审批流程测试表(Salary)表服务接口
 *
 * @author makejava
 * @since 2020-05-09 23:22:47
 */
public interface SalaryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Salary queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Salary> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param salary 实例对象
     * @return 实例对象
     */
    Salary insert(Salary salary);

    /**
     * 修改数据
     *
     * @param salary 实例对象
     * @return 实例对象
     */
    Salary update(Salary salary);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}