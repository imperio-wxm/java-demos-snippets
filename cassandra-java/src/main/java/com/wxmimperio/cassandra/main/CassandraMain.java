package com.wxmimperio.cassandra.main;

import com.datastax.driver.core.*;
import com.wxmimperio.cassandra.connection.CassandraConn;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiximing.imperio on 2016/9/8.
 */
public class CassandraMain {
 /*   public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();
        int i = 0;
        int batchSize = 400;

        //获取session
        Session session = cluster.connect();

        String createKeySpaceSQL = "CREATE KEYSPACE IF NOT EXISTS kafka_crass WITH " +
                "replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS kafka_crass.kafka_table (" +
                "id text," +
                "timestamp text," +
                "topic text," +
                "message text," +
                "data text," +
                "PRIMARY KEY (id,timestamp,data,message)" +
                ") WITH comment='Kafka Table';";

        session.execute(createKeySpaceSQL);
        session.execute(createTableSQL);

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO kafka_crass.kafka_table (id,timestamp,topic,message,data) VALUES(?,?,?,?,?);";
        PreparedStatement prepareBatch = session.prepare(insertSQL);

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        while (i < 4000000) {
            Calendar endCal = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd HH");

            batchStatement.add(prepareBatch.bind(year.format(endCal.getTime()).substring(0,10), "16:" + time.format(endCal.getTime()).substring(3),
                    "topic_002", "hello" + "16:" + time.format(endCal.getTime()).substring(3) + i, year.format(endCal.getTime()).substring(0, 10)));

            //batchStatement.add(prepareBatch.bind("2016-10-02 14", time.format(endCal.getTime()), "topic_002", "hello kafka message this is topic_001 1110724 2016-09-12 16:12:17:245_" + i));

            if (i % batchSize == 0) {

                System.out.println("i = " + i);
                System.out.println("start = " + startTime);

                session.execute(batchStatement);
                batchStatement.clear();

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
    }*/

    public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();

        //获取session
        Session session = cluster.connect();

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        //String countSQL = "select message from kafka_crass.kafka_table where id='2016-09-21' and timestamp > '16:00:00' and timestamp < '16:59:59';";

        String countSQL = "select message from kafka_crass.kafka_table where id='2016-09-21';";

        try {
            System.out.println("start = " + startTime);
            ResultSet rs = session.execute(countSQL);
            List<Row> rowList = rs.all();

            System.out.println("size = " + rowList.size());

            Calendar endcal = Calendar.getInstance();

            long endTime = endcal.getTimeInMillis();
            System.out.println("end = " + endTime);

            long cost = endTime - startTime;
            System.out.println("cost = " + cost);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            cluster.close();
        }
    }
}

class CassHandle implements Runnable {
    /*private BatchStatement batchStatement;
    private Session session;

    CassHandle(BatchStatement batchStatement, Session session) {
        this.batchStatement = batchStatement;
        this.session = session;
    }*/
    private Session session;
    private int i = 0;
    private final BatchStatement batchStatement;
    private PreparedStatement prepareBatch;

    CassHandle(Session session, BatchStatement batchStatement) {
        this.session = session;
        this.batchStatement = batchStatement;
        String insertSQL = "INSERT INTO kafka_crass.kafka_table (id,topic,message) VALUES(?,?,?);";
        prepareBatch = this.session.prepare(insertSQL);
    }

    @Override
    public void run() {
        int batchSize = 10000;

        while (i < 1000000) {
            System.out.println("thread = " + Thread.currentThread().getName());
            batchStatement.add(prepareBatch.bind(String.valueOf(i), "topic_002", "message_" + i));
            if (i % batchSize == 0) {
                synchronized (batchStatement) {
                    session.execute(batchStatement);
                    batchStatement.clear();
                }
            }
            i++;
        }
    }
}
