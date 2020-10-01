/*
 * This file is generated by jOOQ.
 */
package com.study.module.springbootjooq.generate.tables;


import com.study.module.springbootjooq.generate.Keys;
import com.study.module.springbootjooq.generate.Zero;
import com.study.module.springbootjooq.generate.tables.records.TSalaryRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 薪资表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TSalary extends TableImpl<TSalaryRecord> {

    private static final long serialVersionUID = 492787788;

    /**
     * The reference instance of <code>zero.t_salary</code>
     */
    public static final TSalary T_SALARY = new TSalary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSalaryRecord> getRecordType() {
        return TSalaryRecord.class;
    }

    /**
     * The column <code>zero.t_salary.ID</code>.
     */
    public final TableField<TSalaryRecord, Integer> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>zero.t_salary.name</code>. 员工姓名
     */
    public final TableField<TSalaryRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(25), this, "员工姓名");

    /**
     * The column <code>zero.t_salary.position</code>. 职位
     */
    public final TableField<TSalaryRecord, String> POSITION = createField(DSL.name("position"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "职位");

    /**
     * The column <code>zero.t_salary.office</code>. 办公地点
     */
    public final TableField<TSalaryRecord, String> OFFICE = createField(DSL.name("office"), org.jooq.impl.SQLDataType.VARCHAR(25), this, "办公地点");

    /**
     * The column <code>zero.t_salary.age</code>. 年龄
     */
    public final TableField<TSalaryRecord, Integer> AGE = createField(DSL.name("age"), org.jooq.impl.SQLDataType.INTEGER, this, "年龄");

    /**
     * The column <code>zero.t_salary.start_date</code>. 入职日期

     */
    public final TableField<TSalaryRecord, LocalDateTime> START_DATE = createField(DSL.name("start_date"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "入职日期\n");

    /**
     * The column <code>zero.t_salary.salary</code>. 薪水
     */
    public final TableField<TSalaryRecord, Integer> SALARY = createField(DSL.name("salary"), org.jooq.impl.SQLDataType.INTEGER, this, "薪水");

    /**
     * Create a <code>zero.t_salary</code> table reference
     */
    public TSalary() {
        this(DSL.name("t_salary"), null);
    }

    /**
     * Create an aliased <code>zero.t_salary</code> table reference
     */
    public TSalary(String alias) {
        this(DSL.name(alias), T_SALARY);
    }

    /**
     * Create an aliased <code>zero.t_salary</code> table reference
     */
    public TSalary(Name alias) {
        this(alias, T_SALARY);
    }

    private TSalary(Name alias, Table<TSalaryRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSalary(Name alias, Table<TSalaryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("薪资表"), TableOptions.table());
    }

    public <O extends Record> TSalary(Table<O> child, ForeignKey<O, TSalaryRecord> key) {
        super(child, key, T_SALARY);
    }

    @Override
    public Schema getSchema() {
        return Zero.ZERO;
    }

    @Override
    public UniqueKey<TSalaryRecord> getPrimaryKey() {
        return Keys.KEY_T_SALARY_PRIMARY;
    }

    @Override
    public List<UniqueKey<TSalaryRecord>> getKeys() {
        return Arrays.<UniqueKey<TSalaryRecord>>asList(Keys.KEY_T_SALARY_PRIMARY);
    }

    @Override
    public TSalary as(String alias) {
        return new TSalary(DSL.name(alias), this);
    }

    @Override
    public TSalary as(Name alias) {
        return new TSalary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TSalary rename(String name) {
        return new TSalary(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TSalary rename(Name name) {
        return new TSalary(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, String, String, Integer, LocalDateTime, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
