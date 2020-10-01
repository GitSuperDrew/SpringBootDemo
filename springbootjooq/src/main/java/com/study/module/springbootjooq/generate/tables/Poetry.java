/*
 * This file is generated by jOOQ.
 */
package com.study.module.springbootjooq.generate.tables;


import com.study.module.springbootjooq.generate.Keys;
import com.study.module.springbootjooq.generate.Zero;
import com.study.module.springbootjooq.generate.tables.records.PoetryRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Poetry extends TableImpl<PoetryRecord> {

    private static final long serialVersionUID = 544897976;

    /**
     * The reference instance of <code>zero.poetry</code>
     */
    public static final Poetry POETRY = new Poetry();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PoetryRecord> getRecordType() {
        return PoetryRecord.class;
    }

    /**
     * The column <code>zero.poetry.id</code>.
     */
    public final TableField<PoetryRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>zero.poetry.poet_id</code>.
     */
    public final TableField<PoetryRecord, Integer> POET_ID = createField(DSL.name("poet_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>zero.poetry.title</code>.
     */
    public final TableField<PoetryRecord, String> TITLE = createField(DSL.name("title"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>zero.poetry.content</code>.
     */
    public final TableField<PoetryRecord, String> CONTENT = createField(DSL.name("content"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>zero.poetry</code> table reference
     */
    public Poetry() {
        this(DSL.name("poetry"), null);
    }

    /**
     * Create an aliased <code>zero.poetry</code> table reference
     */
    public Poetry(String alias) {
        this(DSL.name(alias), POETRY);
    }

    /**
     * Create an aliased <code>zero.poetry</code> table reference
     */
    public Poetry(Name alias) {
        this(alias, POETRY);
    }

    private Poetry(Name alias, Table<PoetryRecord> aliased) {
        this(alias, aliased, null);
    }

    private Poetry(Name alias, Table<PoetryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Poetry(Table<O> child, ForeignKey<O, PoetryRecord> key) {
        super(child, key, POETRY);
    }

    @Override
    public Schema getSchema() {
        return Zero.ZERO;
    }

    @Override
    public UniqueKey<PoetryRecord> getPrimaryKey() {
        return Keys.KEY_POETRY_PRIMARY;
    }

    @Override
    public List<UniqueKey<PoetryRecord>> getKeys() {
        return Arrays.<UniqueKey<PoetryRecord>>asList(Keys.KEY_POETRY_PRIMARY);
    }

    @Override
    public Poetry as(String alias) {
        return new Poetry(DSL.name(alias), this);
    }

    @Override
    public Poetry as(Name alias) {
        return new Poetry(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Poetry rename(String name) {
        return new Poetry(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Poetry rename(Name name) {
        return new Poetry(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
