package com.wxmimperio.presto.ThreadQuery;

import com.facebook.presto.jdbc.PrestoResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.concurrent.Callable;

public class QueryTask implements Callable<Boolean> {
    private final static Logger logger = LoggerFactory.getLogger(QueryTask.class);

    private Connection conn;
    private String sql;

    public QueryTask(Connection conn, String sql) {
        this.conn = conn;
        this.sql = sql;
    }


    @Override
    public Boolean call() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String queryId = rs.unwrap(PrestoResultSet.class).getQueryId();

            System.out.println(queryId);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String game_id = rs.getString("game_id");
                String pt_id = rs.getString("pt_id");
                String snda_id = rs.getString("snda_id");
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
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
        }
        return true;
    }
}
