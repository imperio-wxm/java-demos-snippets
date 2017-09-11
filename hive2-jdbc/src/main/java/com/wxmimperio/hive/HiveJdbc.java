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
    private static String url = "jdbc:hive2://10.1.8.210:10020";
    private static String user = "";
    private static String password = "";
    private static String sql = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConn();
            stmt = conn.createStatement();

            showTables(stmt, "mir2_consume_days_wk_sp_mid");

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
        sql = "SELECT * FROM " + tableName;
        System.out.println("Running:" + sql);
        stmt.execute("use test");
        ResultSet res = stmt.executeQuery(sql);
        System.out.println("执行 show tables 运行结果:");
        int i = 0;
        while (res.next()) {
            i++;
            if (i >= 10) {
                break;
            }
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
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
