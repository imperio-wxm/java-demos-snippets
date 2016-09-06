package com.wxmimperio.sqlite.connection;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class SQLiteConnectionTest {

    @Test
    public void connectTest() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();
        try {
            System.out.println(conn.getMetaData());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
