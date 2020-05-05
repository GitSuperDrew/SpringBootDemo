package com.study.module.ibeetlsql.service.impl;

import com.study.module.ibeetlsql.dao.UserDao;
import com.study.module.ibeetlsql.entity.User;
import com.study.module.ibeetlsql.service.UserService;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.DebugInterceptor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/5/5 上午 10:43
 */
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    /**
     * 得到所有的用户信息
     *
     * @return 所有用户信息
     */
    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public List<User> findAll() {
        return userDao.all();
    }

    /**
     * 根据姓名，查询用户信息
     *
     * @param name 用户姓名
     * @return 用户信息
     */
    @Override
    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public List<User> selectByNameLike(String name) {
        return userDao.selectByNameLike(name);
    }

    /**
     * 根据用户名称模糊查询
     *
     * @param name 用户名称
     * @return 匹配上的所有用户信息
     */
    @Override
    public List<User> queryFindByNameLike(String name) {
        // TODO 很值得改进的地方：如何直接使用 SqlManager 来进行查询。
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/zero?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String userName = "root";
        String password = "root123456";
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, "", userName, password);
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpagth的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/sql");
        // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, new Interceptor[]{new DebugInterceptor()});

        Query<User> query = sqlManager.query(User.class);
        List<User> list = query.andLike("name", "%" + name + "%").orderBy("id desc").select();
        return list;
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByNameLike(String name) {
        // 第一种方式: 在这里拼接好，如下
        return userDao.findByNameLike("%" + name + "%");
        // 第二种方式：再sql/User.md文档中使用  #%+user_name+%#
    }

    /**
     * 根据ID，得到用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public User selectById(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public User findById_Origin(Integer id) {
        return userDao.unique(id);
    }


}
