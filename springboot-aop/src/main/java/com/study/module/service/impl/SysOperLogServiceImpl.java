package com.study.module.service.impl;

import com.study.module.dao.SysOperLogDao;
import com.study.module.entity.SysOperLog;
import com.study.module.service.SysOperLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志记录(SysOperLog)表服务实现类
 *
 * @author drew
 * @since 2021-01-19 18:38:04
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl implements SysOperLogService {
    @Resource
    private SysOperLogDao sysOperLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysOperLog queryById(Long id) {
        return this.sysOperLogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysOperLog> queryAllByLimit(int offset, int limit) {
        return this.sysOperLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysOperLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysOperLog insert(SysOperLog sysOperLog) {
        this.sysOperLogDao.insert(sysOperLog);
        return sysOperLog;
    }

    /**
     * 修改数据
     *
     * @param sysOperLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysOperLog update(SysOperLog sysOperLog) {
        this.sysOperLogDao.update(sysOperLog);
        return this.queryById(sysOperLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysOperLogDao.deleteById(id) > 0;
    }
}