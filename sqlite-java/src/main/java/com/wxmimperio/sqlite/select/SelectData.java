package com.wxmimperio.sqlite.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class SelectData {

    public static void selectData(Connection connection, String sql) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
