package com.wxmimperio.cassandra.insert;

import com.datastax.driver.core.*;
import com.wxmimperio.cassandra.connection.CassandraConn;

import java.math.BigInteger;


/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class BatchInsert {
    public static boolean batchInsert() {
        BatchStatement batchStatement = new BatchStatement();

        Cluster cluster = CassandraConn.getCluster();

        //获取session
        Session session = null;
        try {
            session = cluster.connect();
            String insertSQL = "INSERT INTO kafka_crass.kafka_table (id,topic,offset) VALUES(?,?,?);";
            PreparedStatement prepareBatch = session.prepare(insertSQL);
            for (int i = 10; i < 20; i++) {
                batchStatement.add(prepareBatch.bind(i, "topic_" + i, (long) (i * 10)));
            }
            session.execute(batchStatement);
            batchStatement.clear();
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
        return false;
    }
}
