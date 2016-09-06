package com.wxmimperio.sqlite.insert;

import com.wxmimperio.sqlite.connection.SQLiteConnection;
import org.junit.Test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class InsertDataTest {

    @Test
    public void insertData() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
        String timestamp = time.format(cal.getTime());

        System.out.println("timestamp=" + timestamp);

        String sql = "INSERT INTO kafka_delay (topic,partition,offset,timestamp) " +
                "VALUES('topic_001',0,1234567,'" + timestamp + "')";
        boolean stmtClose = InsertData.insertData(conn, sql);
        System.out.println(stmtClose);
    }
}
