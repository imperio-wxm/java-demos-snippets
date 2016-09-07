package com.wxmimperio.sqlite.select;

import com.wxmimperio.sqlite.connection.SQLiteConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String sql = "SELECT 'topic_001' FROM kafka_delay WHERE id=222;";

        String getOffsetSQL = "SELECT * FROM kafka_delay WHERE topic='" + "topic_001" +
                "' AND " + "partition=" + 0 + ";";

        try {
            System.out.println(conn.isValid(5000));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> rsList = SelectData.selectData(conn, getOffsetSQL);

        System.out.println(rsList.isEmpty());

        for (Map<String, Object> result : rsList) {
            System.out.println("id=" + (int) result.get("id"));
            System.out.println("topic=" + result.get("topic"));
            System.out.println("partition=" + (int) result.get("partition"));
            System.out.println("offset=" + (long) (int) result.get("offset"));
            System.out.println("timestamp=" + result.get("timestamp"));
            System.out.println();
        }

        try {
            System.out.println(conn.isValid(5000));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectResult() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();

        String sql = "SELECT * FROM kafka_delay";

        List<Map<String, Object>> resultList = SelectResult.selectResult(conn, sql, Map.class);

        for (Map<String, Object> reslut : resultList) {
            System.out.println(reslut.get("id"));
        }
    }
}
