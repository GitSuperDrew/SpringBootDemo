package com.study.module.springbootmybatis.controller;

import com.study.module.springbootmybatis.SexEnum;
import com.study.module.springbootmybatis.entity.Teacher;
import com.study.module.springbootmybatis.entity.TeacherDO;
import com.study.module.springbootmybatis.service.TeacherService;
import com.study.module.springbootmybatis.vo.TeacherVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 教师表(Teacher)表控制层
 *
 * @author makejava
 * @since 2020-11-07 19:04:41
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    /**
     * 服务对象
     */
    @Resource
    private TeacherService teacherService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Teacher selectOne(Long id) {
        return this.teacherService.queryById(id);
    }

    /**
     * REST ful 接口，查看教师详情
     *
     * @param id     教师唯一标识 ID
     * @param delTag 逻辑删除标识
     * @return 教师详情信息
     */
    @GetMapping(value = "/getByIdLogic")
    public TeacherVO getByIdLogic(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "delTag", required = false, defaultValue = "0") Integer delTag) {
        TeacherDO teacherDO =  this.teacherService.getById(id, delTag);
        TeacherVO teacherVO = new TeacherVO();
        // 处理类型转换
        teacherVO.setSex(teacherDO.getSex().getName());
        BeanUtils.copyProperties(teacherDO, teacherVO);
        return teacherVO;
    }

    /**
     * REST ful 接口，查看教师详情
     *
     * @param id 教师唯一标识 ID
     * @return 教师详情信息
     */
    @GetMapping(value = "/getById/{id}")
    public Teacher getById(@PathVariable(value = "id") Long id) {
        return this.teacherService.queryById(id);
    }

    /**
     * 单一新增：教师信息
     *
     * @param teacherVO 教师vo
     * @return 添加到数据库的最终教师信息
     */
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping(value = "/save")
    public TeacherVO saveTeacher(@Valid TeacherVO teacherVO) {
        try {
            Teacher teacher = new Teacher();
            // 对象拷贝
            BeanUtils.copyProperties(teacherVO, teacher);
            teacher.setSex(SexEnum.getEnumById(Integer.parseInt(teacherVO.getSex())).getId());
            teacher.setDelTag(0);
            teacher.setRevision(0);
            teacher = this.teacherService.insert(teacher);

            BeanUtils.copyProperties(teacher, teacherVO);
            teacherVO.setSex(SexEnum.getEnumById(Integer.parseInt(teacherVO.getSex())).getName());
            return teacherVO;
        } catch (Exception e) {
            e.printStackTrace();
            // Spring事务手动回滚，一般写在service层。
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("添加教师发生异常! 异常信息为：{}", e);
            return null;
        }
    }

}
