package com.wxmimperio.presto.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by weiximing.imperio on 2017/8/25.
 */
public class SimpleConnect {
    private final static Logger logger = LoggerFactory.getLogger(SimpleConnect.class);

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.facebook.presto.jdbc.PrestoDriver";
    static final String DB_URL = "jdbc:presto:///cassandra/rtc";

    //  Database credentials
    static final String USER = "presto";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            stmt = conn.createStatement();

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
                logger.info(String.format("%s, %d, %s, %s, %d", message_id, area_id, channel_id, game_id, online_num));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            logger.error(e + "");
        } catch (SQLException e) {
            logger.error(e + "");
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                logger.error(e + "");
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                logger.error(e + "");
            }
        }
    }
}
