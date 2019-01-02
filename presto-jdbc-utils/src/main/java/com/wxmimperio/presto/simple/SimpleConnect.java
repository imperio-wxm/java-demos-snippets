package com.wxmimperio.presto.simple;

import com.facebook.presto.jdbc.PrestoResultSet;
import com.facebook.presto.jdbc.QueryStats;
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
    static final String DB_URL = "jdbc:presto://10.1.8.211:7070/cassandra/rtc";

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
            conn.setClientInfo("applicationName", "presto");

            //STEP 4: Execute a query
            stmt = conn.createStatement();

            //String sql = "select count(game_id),count(snda_id),count(pt_id),max(snda_id),game_id,pt_id,snda_id from hive.dw.pt_login_game where part_date >= '2017-10-01' and part_date <= '2017-10-24' group by game_id,pt_id,snda_id limit 10";
            String sql = "select count(game_id),count(snda_id),count(pt_id),max(snda_id),game_id,pt_id,snda_id from hive.dw.pt_login_game where part_date >= '2017-10-01' and part_date <= '2017-10-24' group by game_id,pt_id,snda_id";
            ResultSet rs = stmt.executeQuery(sql);
            String queryId = rs.unwrap(PrestoResultSet.class).getQueryId();

            System.out.println(queryId);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String game_id = rs.getString("game_id");
                String pt_id = rs.getString("pt_id");
                String snda_id = rs.getString("snda_id");

                //Display values
                //logger.info(String.format("%s, %s, %s", game_id, pt_id, snda_id));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            logger.error(e + "");
        } catch (SQLException e) {
            logger.error(e + "", e);
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
