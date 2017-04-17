package com.wxmimperio.druid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by weiximing.imperio on 2017/4/17.
 */
public class MutilThreadDruid {
    public static void main(String[] args) {
        try {
            MutilThreadDruid.execute(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execute(int times) throws Exception {
        int numOfThreads = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        final TableOperator tableOperator = new TableOperator();
        // get source
        tableOperator.setDataSource(DataSourceUtil.getDataSource());

        boolean createResult = false;
        try {
            tableOperator.createTable();
            createResult = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (createResult) {
            List<Future<Long>> results = new ArrayList<Future<Long>>();
            for (int i = 0; i < times; i++) {
                results.add(executor.submit(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        long begin = System.currentTimeMillis();
                        try {
                            tableOperator.insert();
                            // insertResult = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long end = System.currentTimeMillis();
                        return end - begin;
                    }
                }));
            }
            executor.shutdown();
            while (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)) ;

            long sum = 0;
            for (Future<Long> result : results) {
                sum += result.get();
            }

            System.out.println("number of threads :" + numOfThreads + " times:" + times);
            System.out.println("running time: " + sum + "ms");
            System.out.println("TPS: " + (double) (100000 * 1000) / (double) (sum));
            System.out.println();

            try {
                System.out.println(tableOperator.select());
                //tableOperator.dropTable("test_table");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("初始化数据库失败");
        }
    }
}
