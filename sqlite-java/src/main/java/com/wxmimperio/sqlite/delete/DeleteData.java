package com.wxmimperio.sqlite.delete;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class DeleteData {

    public static boolean deleteData(Connection conn, String sql) {
        Statement stmt = null;
        boolean isClose = true;
        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);

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
