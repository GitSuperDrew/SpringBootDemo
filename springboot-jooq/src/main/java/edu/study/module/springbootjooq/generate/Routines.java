/*
 * This file is generated by jOOQ.
 */
package edu.study.module.springbootjooq.generate;


import edu.study.module.springbootjooq.generate.routines.ShowStuName;
import edu.study.module.springbootjooq.generate.routines.ShowStudent;

import org.jooq.Configuration;
import org.jooq.Field;


/**
 * Convenience access to all stored procedures and functions in zero
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Routines {

    /**
     * Call <code>zero.show_stu_name</code>
     */
    public static String showStuName(Configuration configuration, Integer stuId1) {
        ShowStuName f = new ShowStuName();
        f.setStuId1(stuId1);

        f.execute(configuration);
        return f.getReturnValue();
    }

    /**
     * Get <code>zero.show_stu_name</code> as a field.
     */
    public static Field<String> showStuName(Integer stuId1) {
        ShowStuName f = new ShowStuName();
        f.setStuId1(stuId1);

        return f.asField();
    }

    /**
     * Get <code>zero.show_stu_name</code> as a field.
     */
    public static Field<String> showStuName(Field<Integer> stuId1) {
        ShowStuName f = new ShowStuName();
        f.setStuId1(stuId1);

        return f.asField();
    }

    /**
     * Call <code>zero.show_student</code>
     */
    public static void showStudent(Configuration configuration) {
        ShowStudent p = new ShowStudent();

        p.execute(configuration);
    }
}
