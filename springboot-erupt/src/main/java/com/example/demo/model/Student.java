package com.example.demo.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zl
 * @date 2021/2/27 18:09
 **/
@Erupt(name = "学生管理")
@Table(name = "student")
@Entity
public class Student extends BaseModel {

    /**
     * 学生名字
     */
    @EruptField(
            views = @View(title = "学生名字"),
            edit = @Edit(title = "学生名字")
    )
    private String name;

    /**
     * 学生年龄
     */
    @EruptField(
            views = @View(title = "学生年龄"),
            edit = @Edit(title = "学生年龄")
    )
    private Integer number;

    /**
     * 是否毕业
     */
    @EruptField(
            views = @View(title = "是否毕业"),
            edit = @Edit(title = "是否毕业")
    )
    private Boolean isEnd;

    /**
     * 入学日期
     */
    @EruptField(
            views = @View(title = "入学日期"),
            edit = @Edit(title = "入学日期")
    )
    private Date date;

}

