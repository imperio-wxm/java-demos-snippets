package com.wxmimperio.cassandra.main;

import com.datastax.driver.core.*;
import com.wxmimperio.cassandra.connection.CassandraConn;
import com.wxmimperio.cassandra.select.Query;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiximing.imperio on 2016/9/8.
 */
public class CassandraMain {
/*    public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();
        int i = 0;
        int batchSize = 2000;

        //获取session
        Session session = cluster.connect();

        String createKeySpaceSQL = "CREATE KEYSPACE IF NOT EXISTS kafka_crass WITH " +
                "replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS kafka_crass.topic_002 (" +
                "id text," +
                "timestamp text," +
                "topic text," +
                "message text," +
                "date text," +
                "PRIMARY KEY ((id,topic),date,timestamp,message)" +
                ") WITH comment='Kafka Table';";

        //String createIndex = "CREATE INDEX date ON kafka_crass.topic_002(date);";

        session.execute(createKeySpaceSQL);
        session.execute(createTableSQL);
        //session.execute(createIndex);

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO kafka_crass.topic_002 (id,timestamp,topic,message,date) VALUES(?,?,?,?,?);";
        PreparedStatement prepareBatch = session.prepare(insertSQL);

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        while (i < 5000000) {
            Calendar endCal = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd HH");

            batchStatement.add(prepareBatch.bind(year.format(endCal.getTime()).substring(0, 10) + "16", "16:" + time.format(endCal.getTime()).substring(3),
                    "topic_002", "hello" + "16:" + time.format(endCal.getTime()).substring(3) + i, year.format(endCal.getTime()).substring(0, 10)));

            //batchStatement.add(prepareBatch.bind("2016-10-02 14", time.format(endCal.getTime()), "topic_002", "hello kafka message this is topic_001 1110724 2016-09-12 16:12:17:245_" + i));

            if (i % batchSize == 0) {

                System.out.println("i = " + i);
                System.out.println("start = " + startTime);

                session.execute(batchStatement);
                batchStatement.clear();

                long endTime = endCal.getTimeInMillis();

                System.out.println("end = " + endTime);
                long cost = endTime - startTime;
                System.out.println("cost = " + cost);
            }
            i++;
        }
        Calendar endCal = Calendar.getInstance();
        long endTime = endCal.getTimeInMillis();

        System.out.println("end = " + endTime);
        long cost = endTime - startTime;
        System.out.println("cost = " + cost);
        session.execute(batchStatement);
        batchStatement.clear();
        session.close();
        cluster.close();
    }*/

/*    public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();
        int i = 0;
        int batchSize = 3000;

        //获取session
        Session session = cluster.connect();

        *//*String createKeySpaceSQL = "CREATE KEYSPACE IF NOT EXISTS kafka_cass WITH " +
                "replication = {'class': 'SimpleStrategy', 'replication_factor' : 2};";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS kafka_cass.key_table (" +
                "id text," +
                "timestamp timestamp," +
                "topic text," +
                "message text," +
                "date text," +
                "count int," +
                "PRIMARY KEY ((id,topic),date,timestamp,message)" +
                ") WITH comment='Kafka Table';";*//*

        //String createIndex = "CREATE INDEX date ON kafka_crass.topic_002(date);";

        *//*session.execute(createKeySpaceSQL);
        session.execute(createTableSQL);*//*
        //session.execute(createIndex);

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO kafka_cassandra.mobile_app_deposit_sdo_test_min (time,topic,date,messageId,event_time,ip,receivetime,order_id,user_id,app_id,pay_channel,subject,endpoint_type,endpoint_ip,area_id,client_channel_id,client_promoter_id,client_sdk_from,status,transaction_timestamp,charge_amount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement prepareBatch = session.prepare(insertSQL);

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        while (i < 1000000) {
            Calendar endCal = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd HH");

           *//* batchStatement.add(prepareBatch.bind(year.format(endCal.getTime()).substring(0, 10) + "16", "16:" + time.format(endCal.getTime()).substring(3),
                    "topic_002", "hello" + "16:" + time.format(endCal.getTime()).substring(3) + i, year.format(endCal.getTime()).substring(0, 10)));*//*

            //batchStatement.add(prepareBatch.bind("2016-10-02 14", time.format(endCal.getTime()), "topic_002", "hello kafka message this is topic_001 1110724 2016-09-12 16:12:17:245_" + i));

            Calendar calTest = Calendar.getInstance();

            Object newData = calTest.getTime();

            *//*batchStatement.add(prepareBatch.bind(year.format(endCal.getTime()).substring(0, 10) + "16", newData,
                    "topic_002", "hello" + "16:" + time.format(endCal.getTime()).substring(3) + i, year.format(endCal.getTime()).substring(0, 10), i));*//*

            //05 10 15 20 25 30 35 40
            batchStatement.add(prepareBatch.bind("2016-10-31 18-40","mobile_app_deposit_sdo_test","2016-10-31",String.valueOf(i),"2016-10-31 18:28:32","114.80.132.125",
                    "2016-10-31 18:28:32","991000801PP015161019112828000001","1079331189","991000801","35","公会福利礼包","1","27.43.177.144","112",
                    "G23","0","BAIDU","5","2016-10-31 18:28:32", 3000d));

            if (i % batchSize == 0) {

                System.out.println("i = " + i);
                System.out.println("start = " + startTime);

                session.execute(batchStatement);
                batchStatement.clear();

                long endTime = endCal.getTimeInMillis();

                System.out.println("end = " + endTime);
                long cost = endTime - startTime;
                System.out.println("cost = " + cost);
            }
            i++;
        }
        Calendar endCal = Calendar.getInstance();
        long endTime = endCal.getTimeInMillis();

        System.out.println("end = " + endTime);
        long cost = endTime - startTime;
        System.out.println("cost = " + cost);
        session.execute(batchStatement);
        batchStatement.clear();
        session.close();
        cluster.close();
    }*/

/*    public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();
        int i = 1;
        int batchSize = 3000;

        int hour = 0;
        int min = 0;

        //获取session
        Session session = cluster.connect();

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO kafka_cassandra.mobile_app_deposit_sdo_test_min (time,topic,date,messageId,event_time,ip,receivetime,order_id,user_id,app_id,pay_channel,subject,endpoint_type,endpoint_ip,area_id,client_channel_id,client_promoter_id,client_sdk_from,status,transaction_timestamp,charge_amount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement prepareBatch = session.prepare(insertSQL);

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        while (i <= 20160000) {
            Calendar endCal = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd HH");

            Calendar calTest = Calendar.getInstance();

            Object newData = calTest.getTime();

            if ((i % 70000) == 0) {
                min += 5;
                if (min % 60 == 0) {
                    min = 0;
                }
            }

            if ((i % 840000) == 0) {
                hour++;
                if (hour % 24 == 0) {
                    hour = 0;
                }
            }

            //05 10 15 20 25 30 35 40
            batchStatement.add(prepareBatch.bind("2016-11-01 " + addZero(hour, 2) + "-" + addZero(min, 2), "mobile_app_deposit_sdo_test", "2016-10-31", String.valueOf(i), "2016-10-31 18:28:32", "114.80.132.125",
                    "2016-10-31 18:28:32", "991000801PP015161019112828000001", "1079331189", "991000801", "35", "公会福利礼包", "1", "27.43.177.144", "112",
                    "G23", "0", "BAIDU", "5", "2016-10-31 18:28:32", 3000d));

            if (i % batchSize == 0) {

                System.out.println("i = " + i);
                System.out.println("start = " + startTime);

                session.execute(batchStatement);
                batchStatement.clear();

                long endTime = endCal.getTimeInMillis();

                System.out.println("end = " + endTime);
                long cost = endTime - startTime;
                System.out.println("cost = " + cost);
            }
            i++;
        }

        Calendar endCal = Calendar.getInstance();
        long endTime = endCal.getTimeInMillis();

        System.out.println("end = " + endTime);
        long cost = endTime - startTime;
        System.out.println("cost = " + cost);
        session.execute(batchStatement);
        batchStatement.clear();
        session.close();
        cluster.close();
    }*/

   /* public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();
        int i = 1;
        int batchSize = 3000;

        int hour = 0;
        int min = 0;

        //获取session
        Session session = cluster.connect();

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO kafka_cassandra.mobile_app_deposit_sdo_test_index (cassandra_time,cassandra_hour,cassandra_date,message_id,event_time,ip,receivetime,order_id,user_id,app_id,pay_channel,subject,endpoint_type,endpoint_ip,area_id,client_channel_id,client_promoter_id,client_sdk_from,status,transaction_timestamp,charge_amount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement prepareBatch = session.prepare(insertSQL);

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        while (i <= 20160000) {
            Calendar endCal = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH");
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat cassandra_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Calendar calTest = Calendar.getInstance();

            Object newData = calTest.getTime();

            if ((i % 70000) == 0) {
                min += 5;
                if (min % 60 == 0) {
                    min = 0;
                }
            }

            if ((i % 840000) == 0) {
                hour++;
                if (hour % 24 == 0) {
                    hour = 0;
                }
            }

            //05 10 15 20 25 30 35 40
            *//*batchStatement.add(prepareBatch.bind("2016-11-01 " + addZero(hour, 2) + "-" + addZero(min, 2), addZero(hour, 2), addZero(min, 2), "2016-10-31", String.valueOf(i), "2016-10-31 18:28:32", "114.80.132.125",
                    "2016-10-31 18:28:32", "991000801PP015161019112828000001", "1079331189", "991000801", "35", "公会福利礼包", "1", "27.43.177.144", "112",
                    "G23", "0", "BAIDU", "5", "2016-10-31 18:28:32", 3000d));*//*

            *//*batchStatement.add(prepareBatch.bind(addZero(min, 2), "2016-11-01",addZero(hour, 2), String.valueOf(i), "2016-10-31 18:28:32", "114.80.132.125",
                    "2016-10-31 18:28:32", "991000801PP015161019112828000001", "1079331189", "991000801", "35", "公会福利礼包", "1", "27.43.177.144", "112",
                    "G23", "0", "BAIDU", "5", "2016-10-31 18:28:32", 3000d));*//*

            batchStatement.add(prepareBatch.bind(date.format(newData) + " " + addZero(hour, 2) + ":" + addZero(min, 2), date.format(newData) + " " + addZero(hour, 2), date.format(newData), String.valueOf(i), "2016-10-31 18:28:32", "114.80.132.125",
                    "2016-10-31 18:28:32", "991000801PP015161019112828000001", "1079331189", "991000801", "35", "公会福利礼包", "1", "27.43.177.144", "112",
                    "G23", "0", "BAIDU", "5", "2016-10-31 18:28:32", 3000d));

            if (i % batchSize == 0) {

                System.out.println("i = " + i);
                System.out.println("start = " + startTime);

                session.execute(batchStatement);
                batchStatement.clear();

                long endTime = endCal.getTimeInMillis();

                System.out.println("end = " + endTime);
                long cost = endTime - startTime;
                System.out.println("cost = " + cost);
            }
            i++;
        }

        Calendar endCal = Calendar.getInstance();
        long endTime = endCal.getTimeInMillis();

        System.out.println("end = " + endTime);
        long cost = endTime - startTime;
        System.out.println("cost = " + cost);
        session.execute(batchStatement);
        batchStatement.clear();
        session.close();
        cluster.close();
    }*/

    public static void main(String[] args) {
        Cluster cluster = CassandraConn.getCluster();
        Session session = cluster.connect();

        BatchStatement batchStatement = new BatchStatement();
        String insertSQL = "INSERT INTO rtc.wooolh_olnum_glog (cassandra_time,message_id,area_id,channel_id,event_time,game_id,group_id,online_num) VALUES(?,?,?,?,?,?,?,?) USING TTL 60;";
        PreparedStatement prepareBatch = session.prepare(insertSQL);


        for (int i = 0; i < 75535; i++) {
            Object[] data = {"2017-09-20 15:00", "message_id=" + i, 905, "9187", "2017-09-20 15:00:16", "791000317", 1, 20};

            try {
                batchStatement.add(prepareBatch.bind(Arrays.asList(data).toArray()));
            } catch (Exception e) {
                e.printStackTrace();
                insertData(batchStatement);
                batchStatement.add(prepareBatch.bind(Arrays.asList(data).toArray()));
            }
        }

        /*String sql = "SELECT * FROM rtc.wooolh_olnum_glog LIMIT 10;";

        List<Map<String, Object>> resultMap = Query.query(sql, "wooolh_olnum_glog", "rtc");

        System.out.println(resultMap);*/

        //session.execute(batchStatement);

        insertData(batchStatement);

        session.close();
        cluster.close();
    }


    private static void insertData(BatchStatement statement) {
        if (statement.size() > 0) {
            if (statement.size() > 1000 && statement.size() <= 0xFFFF) {
                System.out.println(statement.size());
                statement.clear();
            } else if (statement.size() % 500 != 0) {
                System.out.println(statement.size());
                statement.clear();
            }
        }
    }


/*    public static void main(String args[]) {

        Cluster cluster = CassandraConn.getCluster();

        //获取session
        Session session = cluster.connect();

        Calendar cal = Calendar.getInstance();

        long startTime = cal.getTimeInMillis();

        //String countSQL = "select message from kafka_crass.kafka_table where id='2016-09-21' and timestamp > '16:00:00' and timestamp < '16:59:59';";

        //String countSQL = "select message from kafka_crass.topic_002 where id = '2016-10-1116' and topic = 'topic_002';";

        //String countSQL = "select message from kafka_crass.topic_002 where date='2016-10-11' allow filtering;";

        String countSQL = "select message_id from kafka_cassandra.chargingcsv_test2;";

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
    }*/

    //日期补零
    public static String addZero(int num, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(num);
        while (stringBuilder.length() < len) {
            stringBuilder.insert(0, "0");
        }
        return stringBuilder.toString();
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
