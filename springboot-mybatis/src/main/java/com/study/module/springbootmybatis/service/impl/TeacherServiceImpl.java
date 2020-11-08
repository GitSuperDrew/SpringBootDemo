package com.study.module.springbootmybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.study.module.springbootmybatis.SexEnum;
import com.study.module.springbootmybatis.dao.TeacherDao;
import com.study.module.springbootmybatis.entity.Teacher;
import com.study.module.springbootmybatis.entity.TeacherDO;
import com.study.module.springbootmybatis.service.TeacherService;
import com.study.module.springbootmybatis.utils.PageVO;
import com.study.module.springbootmybatis.vo.TeacherVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师表(Teacher)表服务实现类
 *
 * @author makejava
 * @since 2020-11-07 19:04:41
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Teacher queryById(Long id) {
        return this.teacherDao.queryById(id);
    }

    /**
     * 获取详情
     *
     * @param id     唯一标识
     * @param delTag 逻辑删除标识
     * @return
     */
    @Override
    public TeacherDO getById(Long id, Integer delTag) {
        if (delTag == null) {
            delTag = 0;
        }
        return this.teacherDao.getById(id, delTag);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Teacher> queryAllByLimit(int offset, int limit) {
        return this.teacherDao.queryAllByLimit(offset, limit);
    }

    /**
     * 分页读取教师集合信息
     *
     * @param pageVO 分页实体
     * @return 分页集合信息
     */
    @Override
    public PageInfo<TeacherVO> queryPage(PageVO pageVO, Teacher teacherParam) {
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getSize()).setOrderBy(pageVO.getOrderBy());
        List<Teacher> teacherList = this.teacherDao.queryAll(teacherParam);
        List<TeacherVO> teacherVOList = Lists.newArrayList();
        for (Teacher teacher : teacherList) {
            TeacherVO teacherVO = new TeacherVO();
            BeanUtils.copyProperties(teacher, teacherVO);
            teacherVO.setSex(SexEnum.getEnumById(teacher.getSex()).getName());
            teacherVOList.add(teacherVO);
        }
        PageInfo<TeacherVO> pageInfo = new PageInfo<>(teacherVOList);
        PageHelper.clearPage();
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    @Override
    public Teacher insert(Teacher teacher) {
        this.teacherDao.insert(teacher);
        return teacher;
    }

    /**
     * 修改数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    @Override
    public Teacher update(Teacher teacher) {
        this.teacherDao.update(teacher);
        return this.queryById(teacher.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.teacherDao.deleteById(id) > 0;
    }
}
