package com.wxmimperio.presto;

import com.wxmimperio.presto.pool.PrestoConnectPool;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * Created by weiximing.imperio on 2017/8/25.
 */
public class PoolTest {

    @Test
    public void vectorTest() {
        Vector<String> pool = new Vector<String>(5);
        for (int i = 0; i < 5; i++) {
            pool.add(String.valueOf(i));
        }

        System.out.println(pool);

        for (int i = 0; i < 10; i++) {
            if (pool.size() > 0) {
                String temp = pool.get(0);
                pool.remove(temp);
            } else {
                System.out.println("no connection available");
            }
        }
    }

    @Test
    public void poolTest() {
        PrestoConnectPool connectPool = PrestoConnectPool.getInstance();

        for (int i = 0; i < 10; i++) {
            Connection connection = connectPool.getConnection();
            System.out.println("++++++" + connectPool.getActive());
            //zookeeperConnPool.closeConnection(zkClient);
            try {
                System.out.println("Sleep...");
                Thread.sleep(2000);

                Statement stmt = connection.createStatement();

                String sql = "SELECT message_id,area_id,channel_id,game_id,online_num FROM cassandra.rtc.wooolh_olnum_glog LIMIT 100";
                ResultSet rs = stmt.executeQuery(sql);

                //STEP 5: Extract data from result set
                while (rs.next()) {
                    //Retrieve by column name
                    String message_id = rs.getString("message_id");
                    int area_id = rs.getInt("area_id");
                    String channel_id = rs.getString("channel_id");
                    String game_id = rs.getString("game_id");
                    int online_num = rs.getInt("online_num");

                    //Display values
                    System.out.println(String.format("%s, %d, %s, %s, %d", message_id, area_id, channel_id, game_id, online_num));
                }
                //STEP 6: Clean-up environment
                rs.close();
                stmt.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            //connectPool.releaseConnection(connection);
        }
    }

    @Test
    public void test() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-11-25 23:59:59"));

        SimpleDateFormat cassandraTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuilder stringBuilder = new StringBuilder("(");
        for (int i = 0; i < 1440; i++) {
            calendar.add(Calendar.MINUTE, 1);
            stringBuilder.append("'").append(cassandraTime.format(calendar.getTime())).append("',");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append(")");

        System.out.println(stringBuilder.toString());
    }
}
