package com.study.module.springbooteasyexcel.service.impl;

import com.study.module.springbooteasyexcel.dao.PoetDao;
import com.study.module.springbooteasyexcel.entity.Poet;
import com.study.module.springbooteasyexcel.service.PoetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Poet)表服务实现类
 *
 * @author makejava
 * @since 2020-10-11 14:17:35
 */
@Service("poetService")
public class PoetServiceImpl implements PoetService {
    @Resource
    private PoetDao poetDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Poet queryById(Integer id) {
        return this.poetDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Poet> queryAllByLimit(int offset, int limit) {
        return this.poetDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param poet 实例对象
     * @return 实例对象
     */
    @Override
    public Poet insert(Poet poet) {
        this.poetDao.insert(poet);
        return poet;
    }

    /**
     * 修改数据
     *
     * @param poet 实例对象
     * @return 实例对象
     */
    @Override
    public Poet update(Poet poet) {
        this.poetDao.update(poet);
        return this.queryById(poet.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.poetDao.deleteById(id) > 0;
    }
}
