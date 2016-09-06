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
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "topic TEXT," +
                "partition INTEGER," +
                "offset INTEGER," +
                "timestamp TEXT);";

        boolean stmtClose = CreateTable.createTable(conn, sql);
        System.out.println(stmtClose);
    }
}
