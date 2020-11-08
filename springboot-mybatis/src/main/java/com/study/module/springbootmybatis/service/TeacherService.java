package com.study.module.springbootmybatis.service;

import com.github.pagehelper.PageInfo;
import com.study.module.springbootmybatis.entity.Teacher;
import com.study.module.springbootmybatis.entity.TeacherDO;
import com.study.module.springbootmybatis.utils.PageVO;
import com.study.module.springbootmybatis.vo.TeacherVO;

import java.util.List;

/**
 * 教师表(Teacher)表服务接口
 *
 * @author makejava
 * @since 2020-11-07 19:04:40
 */
public interface TeacherService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Teacher queryById(Long id);

    /**
     * 获取详情
     *
     * @param id     唯一标识
     * @param delTag 逻辑删除标识
     * @return
     */
    TeacherDO getById(Long id, Integer delTag);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Teacher> queryAllByLimit(int offset, int limit);

    /**
     * 分页读取教师集合信息
     *
     * @param pageVO 分页实体类
     * @return 分页信息
     */
    PageInfo<TeacherVO> queryPage(PageVO<?> pageVO, Teacher teacherParam);

    /**
     * 新增数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    Teacher insert(Teacher teacher);

    /**
     * 修改数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    Teacher update(Teacher teacher);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 删除指定教师
     *
     * @param id 教师ID
     */
    void deleteTeacher(Long id);
}
