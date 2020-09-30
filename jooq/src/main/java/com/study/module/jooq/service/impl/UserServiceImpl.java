package com.study.module.jooq.service.impl;

import com.study.module.jooq.service.UserService;
import com.study.module.jooq.tables.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/9/30 下午 10:43
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    DSLContext dsl;

    /**
     * 给User表 重命名 u 。（类似sql语句中的 user as u）.
     * 注意一点，这个User类是逆向生成的tables包下的，不是pojos包下的User实体类。
     * （逆向工程它会生成两个User类。一个在pojos下，一个再tables下）
     */
    User u = User.USER.as("u");

    /**
     * 删除
     *
     * @param id 用户ID
     */
    @Override
    public void delete(long id) {
        dsl.delete(u).where(u.ID.eq(id));
    }

    /**
     * 增加
     *
     * @param user 需添加的用户信息
     */
    @Override
    public void insert(com.study.module.jooq.tables.pojos.User user) {
        dsl.insertInto(u)
                .columns(u.NAME, u.AGE, u.EMAIL, u.REMOVE_TAG)
                .values(user.getName(), user.getAge(), user.getEmail(), 1)
                .execute();
    }

    /**
     * 更新
     *
     * @param user 最新的用户更新信息
     * @return
     */
    @Override
    public int update(com.study.module.jooq.tables.pojos.User user) {
        dsl.update(u).set((Record) user);
        return 0;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public com.study.module.jooq.tables.pojos.User selectById(long id) {
        List<com.study.module.jooq.tables.pojos.User> result = dsl.select(u.NAME, u.AGE, u.EMAIL)
                .from(u)
                .where(u.ID.eq(id))
                .fetch()
                .into(com.study.module.jooq.tables.pojos.User.class);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 获取分页用户
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页显示条目数
     * @return
     */
    @Override
    public List<com.study.module.jooq.tables.pojos.User> selectAll(int pageNum, int pageSize) {
        List<com.study.module.jooq.tables.pojos.User> result = dsl.select(u.NAME, u.AGE, u.EMAIL)
                .from(u)
                // 排序字段，（根据ID降序排序）
                .orderBy(u.ID.desc())
                // 分页
                .offset(0)
                .limit(10)
                .fetch()
                // 数据类型格式转换
                .into(com.study.module.jooq.tables.pojos.User.class);
        return result;
    }
}
