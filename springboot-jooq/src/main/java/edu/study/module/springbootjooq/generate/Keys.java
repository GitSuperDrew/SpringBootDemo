/*
 * This file is generated by jOOQ.
 */
package edu.study.module.springbootjooq.generate;


import edu.study.module.springbootjooq.generate.tables.Poet;
import edu.study.module.springbootjooq.generate.tables.Poetry;
import edu.study.module.springbootjooq.generate.tables.Student;
import edu.study.module.springbootjooq.generate.tables.TSalary;
import edu.study.module.springbootjooq.generate.tables.User;
import edu.study.module.springbootjooq.generate.tables.WebLog;
import edu.study.module.springbootjooq.generate.tables.records.PoetRecord;
import edu.study.module.springbootjooq.generate.tables.records.PoetryRecord;
import edu.study.module.springbootjooq.generate.tables.records.StudentRecord;
import edu.study.module.springbootjooq.generate.tables.records.TSalaryRecord;
import edu.study.module.springbootjooq.generate.tables.records.UserRecord;
import edu.study.module.springbootjooq.generate.tables.records.WebLogRecord;

import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>zero</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<StudentRecord, Integer> IDENTITY_STUDENT = Identities0.IDENTITY_STUDENT;
    public static final Identity<UserRecord, Long> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<WebLogRecord, Long> IDENTITY_WEB_LOG = Identities0.IDENTITY_WEB_LOG;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PoetRecord> KEY_POET_PRIMARY = UniqueKeys0.KEY_POET_PRIMARY;
    public static final UniqueKey<PoetryRecord> KEY_POETRY_PRIMARY = UniqueKeys0.KEY_POETRY_PRIMARY;
    public static final UniqueKey<StudentRecord> KEY_STUDENT_PRIMARY = UniqueKeys0.KEY_STUDENT_PRIMARY;
    public static final UniqueKey<TSalaryRecord> KEY_T_SALARY_PRIMARY = UniqueKeys0.KEY_T_SALARY_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<WebLogRecord> KEY_WEB_LOG_PRIMARY = UniqueKeys0.KEY_WEB_LOG_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<StudentRecord, Integer> IDENTITY_STUDENT = Internal.createIdentity(Student.STUDENT, Student.STUDENT.STU_ID);
        public static Identity<UserRecord, Long> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
        public static Identity<WebLogRecord, Long> IDENTITY_WEB_LOG = Internal.createIdentity(WebLog.WEB_LOG, WebLog.WEB_LOG.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<PoetRecord> KEY_POET_PRIMARY = Internal.createUniqueKey(Poet.POET, "KEY_poet_PRIMARY", new TableField[] { Poet.POET.ID }, true);
        public static final UniqueKey<PoetryRecord> KEY_POETRY_PRIMARY = Internal.createUniqueKey(Poetry.POETRY, "KEY_poetry_PRIMARY", new TableField[] { Poetry.POETRY.ID }, true);
        public static final UniqueKey<StudentRecord> KEY_STUDENT_PRIMARY = Internal.createUniqueKey(Student.STUDENT, "KEY_student_PRIMARY", new TableField[] { Student.STUDENT.STU_ID }, true);
        public static final UniqueKey<TSalaryRecord> KEY_T_SALARY_PRIMARY = Internal.createUniqueKey(TSalary.T_SALARY, "KEY_t_salary_PRIMARY", new TableField[] { TSalary.T_SALARY.ID }, true);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_user_PRIMARY", new TableField[] { User.USER.ID }, true);
        public static final UniqueKey<WebLogRecord> KEY_WEB_LOG_PRIMARY = Internal.createUniqueKey(WebLog.WEB_LOG, "KEY_web_log_PRIMARY", new TableField[] { WebLog.WEB_LOG.ID }, true);
    }
}
