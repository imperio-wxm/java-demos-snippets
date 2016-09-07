package com.wxmimperio.cassandra.select;

import com.datastax.driver.core.*;
import com.wxmimperio.cassandra.connection.CassandraConn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class Query {

    public static List<Map<String, Object>> query(String sql, String table, String keyspace) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        Cluster cluster = CassandraConn.getCluster();

        //获取session
        Session session = null;
        try {
            session = cluster.connect();
            ResultSet rs = session.execute(sql);
            List<Row> rows = rs.all();

            for (Row row : rows) {
                Map<String, Object> resultMap = getResults(row, cluster, keyspace, table);
                resultList.add(resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (cluster != null) {
                cluster.close();
            }
        }
        return resultList;
    }

    private static Map<String, Object> getResults(Row row, Cluster cluster, String keyspace, String table) {
        Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
        List<ColumnMetadata> columnMetadatas = cluster.getMetadata()
                .getKeyspace(keyspace).getTable(table).getColumns();
        String columnName;

        for (ColumnMetadata columnMetadata : columnMetadatas) {
            columnName = columnMetadata.getName();
            resultMap.put(columnName, row.getObject(columnName));
        }

        return resultMap;
    }
}
