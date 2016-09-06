package com.wxmimperio.sqlite.select;

import com.wxmimperio.sqlite.connection.SQLiteConnection;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class SelectDataTest {

    @Test
    public void selectDataTest() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();

        String sql = "SELECT * FROM kafka_delay WHERE topic='topic_001' AND partition=0;";

        List<Map<String, Object>> rsList = SelectData.selectData(conn, sql);
        for (Map<String, Object> result : rsList) {
            System.out.println("id=" + (int) result.get("id"));
            System.out.println("topic=" + result.get("topic"));
            System.out.println("partition=" + (int) result.get("partition"));
            System.out.println("offset=" + (long) result.get("offset"));
            System.out.println("timestamp=" + result.get("timestamp"));
            System.out.println();
        }
    }
}
