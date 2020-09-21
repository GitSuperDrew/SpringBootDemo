package com.study.module.mybatis.service.impl;

import com.study.module.mybatis.dao.WebLogDao;
import com.study.module.mybatis.entity.WebLog;
import com.study.module.mybatis.service.WebLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统日志表(WebLog)表服务实现类
 *
 * @author makejava
 * @since 2020-09-21 21:31:03
 */
@Service("webLogService")
public class WebLogServiceImpl implements WebLogService {
    @Resource
    private WebLogDao webLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WebLog queryById(Long id) {
        return this.webLogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<WebLog> queryAllByLimit(int offset, int limit) {
        return this.webLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param webLog 实例对象
     * @return 实例对象
     */
    @Override
    public WebLog insert(WebLog webLog) {
        this.webLogDao.insert(webLog);
        return webLog;
    }

    /**
     * 修改数据
     *
     * @param webLog 实例对象
     * @return 实例对象
     */
    @Override
    public WebLog update(WebLog webLog) {
        this.webLogDao.update(webLog);
        return this.queryById(webLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.webLogDao.deleteById(id) > 0;
    }
}
