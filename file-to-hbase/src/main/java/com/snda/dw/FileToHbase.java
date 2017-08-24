package com.snda.dw;

import com.google.common.collect.Lists;
import com.snda.dw.pojo.Bslog;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import java.io.*;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/2/24.
 */
public class FileToHbase {
    //private static final String HBASE_ZOOKEEPER_QUORUM = "10.1.9.92";
    //private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "2222";
    private static final String HBASE_ZOOKEEPER_QUORUM = "10.1.10.141";
    private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "2190";
    private static final String HBASE_TABLE_NAME = "dqx_deposit_detail_20170222_2";
    private static final int BATCH_SIZE = 100;
    private static final String FILE_PATH = System.getProperty("user.dir") + "/file/data_00_13.txt";
    private HTable table;

    FileToHbase() throws IOException {
        initHBaseTable();
    }

    final static byte COLUMN_SEPARATOR = 0x01;

    private void put(List<Bslog> cache) throws IOException {
        List<Put> putList = Lists.newArrayList();
        for (Bslog log : cache) {
            String ptId = log.getPt_id();
            String orderId = log.getOrder_id();
            ByteArrayOutputStream keyOs = new ByteArrayOutputStream();
            keyOs.write(ptId.getBytes());
            keyOs.write(COLUMN_SEPARATOR);
            keyOs.write(orderId.getBytes());
            keyOs.flush();
            byte[] key = keyOs.toByteArray();
            keyOs.close();

            String time = log.getDeposit_time();
            String price = log.getDeposit_value() + "";
            ByteArrayOutputStream valueOs = new ByteArrayOutputStream();
            valueOs.write(time.getBytes());
            valueOs.write(COLUMN_SEPARATOR);
            valueOs.write(price.getBytes());
            valueOs.flush();
            byte[] value = valueOs.toByteArray();
            valueOs.close();

            Put put = new Put(key);
            System.out.println("======== key = " + new String(key) + "    ========= settle_time = " + time);
            put.add("cf".getBytes(), "v".getBytes(), value);
            putList.add(put);
        }
        if (!putList.isEmpty()) {
            table.put(putList);
        }
    }

    private void start() throws IOException {
        readTxtFile(FILE_PATH);
    }

    private void stop() throws IOException {
        table.close();
    }

    private void initHBaseTable() throws IOException {
        System.out.println("begin initHBaseTable.....");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
        conf.set("hbase.zookeeper.property.clientPort", HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
        table = new HTable(conf, HBASE_TABLE_NAME);
        System.out.println("finish initHBaseTable.....");
    }

    private void readTxtFile(String filePath) {
        List<Bslog> cache = Lists.newArrayList();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int lineNum = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineNum > 0) {
                        String[] message = lineTxt.split("\t", -1);
                        Bslog bslog = new Bslog();
                        bslog.setPt_id(message[0]);
                        bslog.setOrder_id(message[1]);
                        bslog.setDeposit_time(message[2]);
                        bslog.setDeposit_value(message[3]);
                        cache.add(bslog);
                    }
                    lineNum++;
                }
                System.out.printf("line number = " + lineNum);
                bufferedReader.close();
                read.close();

                //insert
                put(cache);

                System.out.println(cache);

                stop();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        new FileToHbase().start();
    }
}
