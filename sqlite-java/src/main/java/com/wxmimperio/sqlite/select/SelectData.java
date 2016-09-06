package com.wxmimperio.sqlite.select;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class SelectData {

    public static List<Map<String, Object>> selectData(Connection connection, String sql) {
        Statement stmt = null;

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            stmt = connection.createStatement();
            connection.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery(sql);

            connection.commit();
            connection.setAutoCommit(true);

            while (rs.next()) {
                Map<String, Object> result = new ConcurrentHashMap<String, Object>();
                result.put("id", rs.getInt("id"));
                result.put("topic", rs.getString("topic"));
                result.put("partition", rs.getInt("partition"));
                result.put("offset", rs.getLong("offset"));
                result.put("timestamp", rs.getTimestamp("timestamp"));
                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }
}
