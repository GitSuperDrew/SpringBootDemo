package com.study.module.mapstruct.converter;

import com.study.module.mapstruct.dto.StudentDTO;
import com.study.module.mapstruct.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/7/26 上午 10:24
 */
@Mapper
public interface StudentConverter {
    StudentConverter INSTANCE = Mappers.getMapper(StudentConverter.class);

    @Mappings({
            @Mapping(source = "stuId", target = "id"),
            @Mapping(source = "stuName", target = "name"),
            @Mapping(source = "stuAge", target = "age"),
            @Mapping(source = "stuSex", target = "sex")
    })
    StudentDTO demain2DTO(Student student);

    List<StudentDTO> domain2dto(List<Student> studentDTOs);

}
