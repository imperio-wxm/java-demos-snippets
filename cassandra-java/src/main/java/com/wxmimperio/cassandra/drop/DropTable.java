package com.wxmimperio.cassandra.drop;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.wxmimperio.cassandra.connection.CassandraConn;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class DropTable {
    public static boolean dropTable(String sql) {
        Cluster cluster = CassandraConn.getCluster();
        boolean isExecute = false;

        //获取session
        Session session = null;

        try {
            session = cluster.connect();
            session.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (cluster != null) {
                cluster.close();
                isExecute = true;
            }
        }
        return isExecute;
    }
}
