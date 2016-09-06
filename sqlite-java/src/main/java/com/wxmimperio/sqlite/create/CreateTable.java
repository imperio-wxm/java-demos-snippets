package com.wxmimperio.sqlite.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class CreateTable {
    public static boolean createTable(Connection conn, String sql) {
        Statement stmt = null;
        boolean isClose = false;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    isClose = stmt.isClosed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isClose;
    }
}
