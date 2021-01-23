package edu.study.module.springbootsecurity.service;

import edu.study.module.springbootsecurity.entity.SysRolePermission;

import java.util.List;

/**
 * (SysRolePermission)表服务接口
 *
 * @author drew
 * @since 2021-01-23 11:12:56
 */
public interface SysRolePermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRolePermission queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysRolePermission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysRolePermission 实例对象
     * @return 实例对象
     */
    SysRolePermission insert(SysRolePermission sysRolePermission);

    /**
     * 修改数据
     *
     * @param sysRolePermission 实例对象
     * @return 实例对象
     */
    SysRolePermission update(SysRolePermission sysRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}