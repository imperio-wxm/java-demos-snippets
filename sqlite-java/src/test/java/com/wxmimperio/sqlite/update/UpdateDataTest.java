package com.wxmimperio.sqlite.update;

import com.wxmimperio.sqlite.connection.SQLiteConnection;
import com.wxmimperio.sqlite.select.SelectData;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class UpdateDataTest {

    @Test
    public void updateTest() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();

        String sql = "UPDATE kafka_delay SET offset=00000 WHERE topic='topic_001';";
        boolean isClose = UpdateData.updateData(conn, sql);
        System.out.println(isClose);

        SQLiteConnection reSqLiteConnection = new SQLiteConnection();
        Connection reConn = reSqLiteConnection.getConnection();

        String reSql = "SELECT * FROM kafka_delay;";

        List<Map<String, Object>> rsList = SelectData.selectData(reConn, reSql);
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
