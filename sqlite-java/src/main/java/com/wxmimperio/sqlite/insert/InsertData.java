package com.wxmimperio.sqlite.insert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class InsertData {

    public static boolean insertData(Connection conn, String sql) {
        Statement stmt = null;
        boolean isClose = false;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            conn.setAutoCommit(true);
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
