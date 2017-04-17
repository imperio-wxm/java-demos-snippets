package com.wxmimperio.hive;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2017/4/11.
 */
public class HiveJdbc {
    private static final Logger LOG = LoggerFactory.getLogger(HiveJdbc.class);

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://127.0.0.1:10020";
    private static String user = "hadoop";
    private static String password = "";
    private static String sql = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConn();
            stmt = conn.createStatement();

            showTables(stmt, "");

        } catch (ClassNotFoundException e) {
            LOG.error(driverName + " not found!", e);
            System.exit(1);
        } catch (SQLException e) {
            LOG.error("Connection error!", e);
            System.exit(1);
        } finally {
            close(conn, stmt);
        }
    }

    private static void showTables(Statement stmt, String tableName) throws SQLException {
        sql = "show tables '" + tableName + "'";
        System.out.println("Running:" + sql);
        stmt.executeQuery("use dw");
        ResultSet res = stmt.executeQuery(sql);
        System.out.println("执行 show tables 运行结果:");
        if (res.next()) {
            System.out.println(res.getString(1));
        }
    }

    private static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    private static void close(Connection conn, Statement stmt) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
