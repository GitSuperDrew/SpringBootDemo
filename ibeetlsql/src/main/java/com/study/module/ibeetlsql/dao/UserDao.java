package com.study.module.ibeetlsql.dao;

import com.study.module.ibeetlsql.entity.User;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import javax.management.Query;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/5/5 上午 10:34
 */
public interface UserDao extends BaseMapper<User> {
    @SqlStatement
    List<User> selectAll();

    @Sql(value = "select * from user where name like ?")
    List<User> findByNameLike(@Param("name") String name);

    @SqlStatement(params = "name")
    User selectByName(String name);

    @SqlStatement(params = "name")
    List<User> selectByNameLike(String name);

    @Sql(value = "select * from user where `id` = ?")
    User findById(@Param("id") Integer id);

    @SqlStatement(params = "id")
    User selectById(Integer id);

}
