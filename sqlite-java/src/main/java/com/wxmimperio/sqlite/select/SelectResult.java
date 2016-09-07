package com.wxmimperio.sqlite.select;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2016/9/6.
 */
public class SelectResult {


    public static <E> E selectResult(Connection connection, String sql, Class resultClass) {
        Statement stmt = null;
        List<E> resultList = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            connection.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery(sql);

            if (resultClass == Map.class) {
                if (rs.next()) {
                    resultList.add((E) getResultMap(rs));

                    System.out.println("===========");
                }
            }

            connection.commit();
            connection.setAutoCommit(true);

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
        return (E) resultList;
    }

    private static Map<String, Object> getResultMap(ResultSet rs) throws SQLException {
        Map<String, Object> rawMap = new ConcurrentHashMap<String, Object>();
        // 表对象信息
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();

        for (int i = 1; i <= count; i++) {
            String key = rsmd.getColumnLabel(i);
            Object value = rs.getObject(key);
            rawMap.put(key, value);
        }
        return rawMap;
    }
}
