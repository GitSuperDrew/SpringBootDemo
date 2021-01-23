package edu.study.module.springbootsecurity.dao;

import edu.study.module.springbootsecurity.entity.SysPermission;
import edu.study.module.springbootsecurity.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.security.Permission;
import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author drew
 * @since 2021-01-23 11:09:28
 */
@Mapper
public interface SysUserDao {

    /**
     * 用户名查询用户信息
     *
     * @param userName 用户名称
     * @return
     */
    @Select("select * from sys_user where username = #{userName}")
    SysUser selectByUserName(String userName);

    /**
     * 用户名查询当前用户的权限信息
     *
     * @param userName 用户名
     * @return
     */
    @Select("select sys_permission.* FROM" +
            "    sys_user  u" +
            "    INNER JOIN sys_user_role ON u.id = sys_user_role.uid" +
            "    INNER JOIN sys_role_permission on sys_user_role.rid = sys_role_permission.rid" +
            "    INNER JOIN sys_permission on sys_role_permission.pid = sys_permission.id" +
            "    WHERE u.username = #{userName};")
    List<SysPermission> findPermissionByUserName(String userName);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int add(SysUser sysUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUser> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SysUser> entities);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}