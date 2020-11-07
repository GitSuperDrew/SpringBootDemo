package com.study.module.springbootmybatis.handler;

import com.study.module.springbootmybatis.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 * @date 2020/11/7 下午 8:00
 */
@MappedJdbcTypes(JdbcType.INTEGER) // 声明 JdbcType 为整型
@MappedTypes(value = SexEnum.class) // 声明 JavaType 为 SexEnum
public class SexTypeHandler extends BaseTypeHandler<SexEnum> {
    /**
     * 通过非空性别参数
     *
     * @param preparedStatement
     * @param i
     * @param sexEnum
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SexEnum sexEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, sexEnum.getId());
    }

    /**
     * 通过列名读取性别
     *
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int sex = resultSet.getInt(s);
        if (sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /**
     * 通过下标读取性别
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int sex = resultSet.getInt(i);
        if (sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /**
     * 通过存储过程读取性别
     *
     * @param callableStatement
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int sex = callableStatement.getInt(i);
        if (sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(i);
    }
}
