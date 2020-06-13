package com.jiangfeixiang.mpdemo.springbootmp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import com.jiangfeixiang.mpdemo.springbootmp.mapper.StudentMapper;
import com.jiangfeixiang.mpdemo.springbootmp.service.IStudentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author zhongl
 * @since 2019-06-16
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
