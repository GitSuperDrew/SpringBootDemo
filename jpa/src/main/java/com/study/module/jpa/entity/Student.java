package com.study.module.jpa.entity;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
@Entity(name = "student")
public class Student {
    @Id
    private Integer stuId;
    @Column(name = "stu_name")
    private String stuName;
    @Column(name = "stu_age")
    private Integer stuAge;

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("stuId", this.getStuId());
        result.put("stuName", this.getStuName());
        result.put("stuAge", this.getStuAge());
        return result;
    }

    public Map<String, Object> toMapPlus() {
        Map<String, Object> result = ImmutableMap.of("stuId", this.getStuId(), "stuName", this.getStuName(),
                "stuAge", this.getStuAge());
        return result;
    }
}
