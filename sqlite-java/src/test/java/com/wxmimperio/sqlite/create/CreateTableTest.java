package com.wxmimperio.sqlite.create;

import com.wxmimperio.sqlite.connection.SQLiteConnection;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class CreateTableTest {

    @Test
    public void createTable() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();

        String sql = "CREATE TABLE IF NOT EXISTS kafka_delay (" +
                "id INT PRIMARY KEY NOT NULL," +
                "topic CHAR(50)," +
                "partition INT," +
                "offset INTEGER," +
                "timestamp TIMESTAMP);";

        boolean stmtClose = CreateTable.createTable(conn, sql);
        System.out.println(stmtClose);
    }
}
