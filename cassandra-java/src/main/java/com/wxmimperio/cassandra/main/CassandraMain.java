package com.wxmimperio.cassandra.main;

import com.datastax.driver.core.*;
import com.wxmimperio.cassandra.connection.CassandraConn;

import java.util.Calendar;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/8.
 */
public class CassandraMain {
    public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();

        //获取session
        Session session = cluster.connect();

        int i = 0;
        int batchSize = 8000;

        //SQL
        //SQL
        String createKeySpaceSQL = "CREATE KEYSPACE IF NOT EXISTS kafka_crass WITH " +
                "replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS kafka_crass.kafka_table (" +
                "id text PRIMARY KEY," +
                "topic text," +
                "message text," +
                ") WITH comment='Kafka Table';";

        String countSQL = "SELECT * FROM kafka_crass.kafka_table;";

        session.execute(createKeySpaceSQL);
        session.execute(createTableSQL);

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO kafka_crass.kafka_table (id,topic,message) VALUES(?,?,?);";
        PreparedStatement prepareBatch = session.prepare(insertSQL);


        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        /*try {
            System.out.println("start = " + startTime);
            ResultSet rs = session.execute(countSQL);
            List<Row> rowList = rs.all();

            System.out.println("size = " + rowList.size());

            Calendar endcal = Calendar.getInstance();

            long endTime = endcal.getTimeInMillis();
            System.out.println("end = " + endTime);

            long cost = (endTime - startTime) / 1000;
            System.out.println("cost = " + cost);
            *//*session.execute(createKeySpaceSQL);
            session.execute(createTableSQL);*//*
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            cluster.close();
        }*/

        while (i < 1000000) {
            batchStatement.add(prepareBatch.bind(String.valueOf(i), "topic_002", "message_" + i));

            if (i % batchSize == 0) {

                System.out.println("i = " + i);
                System.out.println("start = " + startTime);

                session.execute(batchStatement);
                batchStatement.clear();

                Calendar endCal = Calendar.getInstance();
                long endTime = endCal.getTimeInMillis();

                System.out.println("end = " + endTime);
                long cost = (endTime - startTime) / 1000;
                System.out.println("cost = " + cost);
            }
            i++;
        }
        Calendar endCal = Calendar.getInstance();
        long endTime = endCal.getTimeInMillis();

        System.out.println("end = " + endTime);
        long cost = (endTime - startTime) / 1000;
        System.out.println("cost = " + cost);
        session.execute(batchStatement);
        batchStatement.clear();
        session.close();
        cluster.close();
    }
}
