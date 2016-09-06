package com.wxmimperio.sqlite.delete;

import com.wxmimperio.sqlite.connection.SQLiteConnection;
import com.wxmimperio.sqlite.select.SelectData;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class DeleteDataTest {

    @Test
    public void deleteTest() {
        SQLiteConnection sqLiteConnection = new SQLiteConnection();
        Connection conn = sqLiteConnection.getConnection();

        String sql = "DELETE FROM kafka_delay WHERE topic='topic_002';";

        boolean isClose = DeleteData.deleteData(conn, sql);
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
