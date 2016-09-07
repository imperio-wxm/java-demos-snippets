package com.wxmimperio.cassandra.keyspacetable;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.wxmimperio.cassandra.connection.CassandraConn;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class KeySpaceAndTable {

    public static boolean createKandT() {
        Cluster cluster = CassandraConn.getCluster();
        boolean isExecute = false;

        //获取session
        Session session = null;

        //SQL
        String createKeySpaceSQL = "CREATE KEYSPACE IF NOT EXISTS kafka_crass WITH " +
                "replication = {'class': 'SimpleStrategy', 'replication_factor' : 1}";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS kafka_crass.kafka_table (" +
                "id int PRIMARY KEY," +
                "topic text," +
                "offset varint" +
                ") WITH comment='Kafka Table';";

        try {
            session = cluster.connect();
            session.execute(createKeySpaceSQL);
            session.execute(createTableSQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (cluster != null) {
                cluster.close();
                isExecute = true;
            }
        }
        return isExecute;
    }
}
