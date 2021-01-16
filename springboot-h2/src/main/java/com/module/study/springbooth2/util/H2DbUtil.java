package com.module.study.springbooth2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 数据库操作工具类
 *
 * @author d
 */
public class H2DbUtil {

    private static final Logger log = LoggerFactory.getLogger(H2DbUtil.class);
    private static Connection myConnection = null;

    // 初始化并加载驱动
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            log.debug("发生异常", ex);
        }
    }

    /**
     * 启动连接
     *
     * @param theDbPath
     * @throws SQLException
     */
    public static void setupConn(String theDbPath) throws SQLException {
        if (null == myConnection || myConnection.isClosed()) {
            myConnection = DriverManager.getConnection("jdbc:h2:" + theDbPath);
        }
    }

    /**
     * 创建SQL连接语句
     *
     * @return
     * @throws SQLException
     */
    public static Statement getStatement() throws SQLException {
        if (null == myConnection || myConnection.isClosed()) {
            SQLException ex = new SQLException("No valid database connection!");
            log.debug("获取失败", ex);
            throw ex;
        }

        return myConnection.createStatement();
    }

    /**
     * 关闭连接
     *
     * @throws SQLException
     */
    public static void closeConn() throws SQLException {
        myConnection.close();
    }

    /**
     * 启动数据库的脚本
     *
     * @param theDbPath 数据库所在的路径
     * @throws SQLException
     */
    public static void setupDB(String theDbPath) throws SQLException {

        setupConn(theDbPath);
        runScript("init.sql");
    }

    /**
     * 运行SQL脚本
     *
     * @param thePath SQL脚本所在的文件路径
     * @throws SQLException
     */
    public static void runScript(String thePath) throws SQLException {

        Statement stat = getStatement();
        stat.execute("runscript from '" + thePath + "'");
        stat.close();
    }

    /**
     * TODO：重置数据库
     *
     * @param theDbPath 数据库路径
     * @throws Exception
     */
    public static void resetDB(String theDbPath) throws Exception {
        // to separate the dbname from the path
        int lastSlash = theDbPath.lastIndexOf('/');

        // DeleteDbFiles.execute(theDbPath.substring(0, lastSlash), theDbPath.substring(lastSlash), true);

        setupDB(theDbPath);
    }
}
