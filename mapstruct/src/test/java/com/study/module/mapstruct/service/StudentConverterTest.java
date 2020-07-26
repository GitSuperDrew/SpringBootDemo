package com.study.module.mapstruct.service;

import com.study.module.mapstruct.dao.StudentDao;
import com.study.module.mapstruct.dto.StudentDTO;
import com.study.module.mapstruct.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/7/26 上午 10:30
 */
@SpringBootTest
class StudentConverterTest {
    @Resource
    private StudentDao studentDao;

    @Test
    void demain2DTO() {
        Student student = Student.builder()
                .stuId(1)
                .stuName("Drew")
                .stuAge(12)
                .stuSex("female")
                .build();
        StudentDTO studentDTO = StudentConverter.INSTANCE.demain2DTO(student);
        System.out.println(studentDTO);
    }

    @Test
    void toStudentDTO() {
        Student student = studentDao.queryById(2);
        System.out.println(StudentConverter.INSTANCE.demain2DTO(student));
    }

    @Test
    void toStuDtoList() {
        Student student1 = studentDao.queryById(2);
        Student student2 = studentDao.queryById(3);
        List<Student> students = Arrays.asList(student1, student2);
        List<StudentDTO> studentDTOS = StudentConverter.INSTANCE.domain2dto(students);
        System.out.println(studentDTOS);
    }
}