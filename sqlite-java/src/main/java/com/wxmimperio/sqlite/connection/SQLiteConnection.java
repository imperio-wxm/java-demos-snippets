package com.wxmimperio.sqlite.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
class SQLiteConnection {

    private final static Logger logger = Logger.getLogger(SQLiteConnection.class);
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    private static String propFile = System.getProperty("user.dir") + "/conf/";

    Connection getConnection() {
        Connection conn = threadLocal.get();

        try {
            Class.forName("org.sqlite.JDBC");
            if (conn == null || conn.isClosed()) {
                try {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection("jdbc:sqlite:" + propFile + "Database.db");
                    threadLocal.set(conn);
                } catch (Exception e) {
                    logger.error("获取JDBC连接出错", e);
                    throw e;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
