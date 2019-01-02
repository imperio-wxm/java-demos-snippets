package com.wxmimperio.presto.ThreadQuery;

import com.facebook.presto.jdbc.internal.guava.collect.Lists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadQueryMain {

    private static ExecutorService executorService = Executors.newFixedThreadPool(20);
    private static final String DB_URL = "jdbc:presto://10.1.8.211:7070/cassandra/rtc";
    private static final String JDBC_DRIVER = "com.facebook.presto.jdbc.PrestoDriver";
    private static final String USER = "yanagishima";
    private static final String PASS = "";

    public static void main(String[] args) throws Exception {
        //String sql = "select count(game_id),count(snda_id),count(pt_id),max(snda_id),game_id,pt_id,snda_id from hive.dw.pt_login_game where part_date >= '2017-10-01' and part_date <= '2017-10-24' group by game_id,pt_id,snda_id";
        Class.forName(JDBC_DRIVER);
        String sql = "select count(game_id),count(snda_id),count(pt_id),max(snda_id),game_id,pt_id,snda_id from hive.dw.pt_login_game where part_date = '2017-10-01'  group by game_id,pt_id,snda_id limit 10";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        conn.setClientInfo("ApplicationName","yanagishima");
        System.out.println(conn.getClientInfo());

        List<Future> futures = Lists.newArrayList();
        for (int i = 0; i < 15; i++) {
            futures.add(executorService.submit(new QueryTask(conn, sql)));
        }

        for (Future future : futures) {
            System.out.println(future.get());
        }
        executorService.shutdown();
        conn.close();
    }
}
