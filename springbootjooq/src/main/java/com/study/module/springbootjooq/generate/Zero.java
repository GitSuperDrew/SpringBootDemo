/*
 * This file is generated by jOOQ.
 */
package com.study.module.springbootjooq.generate;


import com.study.module.springbootjooq.generate.tables.PdmanDbVersion;
import com.study.module.springbootjooq.generate.tables.Poet;
import com.study.module.springbootjooq.generate.tables.Poetry;
import com.study.module.springbootjooq.generate.tables.Student;
import com.study.module.springbootjooq.generate.tables.TSalary;
import com.study.module.springbootjooq.generate.tables.User;
import com.study.module.springbootjooq.generate.tables.WebLog;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Zero extends SchemaImpl {

    private static final long serialVersionUID = -819695736;

    /**
     * The reference instance of <code>zero</code>
     */
    public static final Zero ZERO = new Zero();

    /**
     * The table <code>zero.pdman_db_version</code>.
     */
    public final PdmanDbVersion PDMAN_DB_VERSION = PdmanDbVersion.PDMAN_DB_VERSION;

    /**
     * The table <code>zero.poet</code>.
     */
    public final Poet POET = Poet.POET;

    /**
     * The table <code>zero.poetry</code>.
     */
    public final Poetry POETRY = Poetry.POETRY;

    /**
     * The table <code>zero.student</code>.
     */
    public final Student STUDENT = Student.STUDENT;

    /**
     * 薪资表
     */
    public final TSalary T_SALARY = TSalary.T_SALARY;

    /**
     * 用户表
     */
    public final User USER = User.USER;

    /**
     * 系统日志表
     */
    public final WebLog WEB_LOG = WebLog.WEB_LOG;

    /**
     * No further instances allowed
     */
    private Zero() {
        super("zero", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            PdmanDbVersion.PDMAN_DB_VERSION,
            Poet.POET,
            Poetry.POETRY,
            Student.STUDENT,
            TSalary.T_SALARY,
            User.USER,
            WebLog.WEB_LOG);
    }
}
