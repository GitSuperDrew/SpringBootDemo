package com.study.module.mybatis.service.impl;

import com.study.module.mybatis.entity.TSalary;
import com.study.module.mybatis.dao.TSalaryDao;
import com.study.module.mybatis.service.TSalaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 薪资表(TSalary)表服务实现类
 *
 * @author makejava
 * @since 2020-04-25 22:08:25
 */
@Service("tSalaryService")
public class TSalaryServiceImpl implements TSalaryService {
    @Resource
    private TSalaryDao tSalaryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TSalary queryById(Integer id) {
        return this.tSalaryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TSalary> queryAllByLimit(int offset, int limit) {
        return this.tSalaryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tSalary 实例对象
     * @return 实例对象
     */
    @Override
    public TSalary insert(TSalary tSalary) {
        this.tSalaryDao.insert(tSalary);
        return tSalary;
    }

    /**
     * 修改数据
     *
     * @param tSalary 实例对象
     * @return 实例对象
     */
    @Override
    public TSalary update(TSalary tSalary) {
        this.tSalaryDao.update(tSalary);
        return this.queryById(tSalary.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tSalaryDao.deleteById(id) > 0;
    }
}