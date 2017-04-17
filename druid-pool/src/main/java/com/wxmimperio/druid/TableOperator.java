package com.wxmimperio.druid;

import com.google.gson.JsonObject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/17.
 */
public class TableOperator {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TableOperator() {
    }

    /**
     * @throws Exception
     */
    public void insert() throws Exception {
        StringBuffer ddl = new StringBuffer();
        ddl.append("INSERT INTO test_table (name,age,gender) VALUES ('wxmimperio',25,'男')");
        Connection conn = dataSource.getConnection();
        System.out.println(ddl.toString());
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);
        stmt.executeUpdate(ddl.toString());
        conn.commit();
        conn.setAutoCommit(true);
        closeResource(conn, stmt);
    }

    /**
     * @param tableName
     * @throws SQLException
     */
    public void dropTable(String tableName) throws SQLException {
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DROP TABLE " + tableName);
        closeResource(conn, stmt);
    }

    /**
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        StringBuffer ddl = new StringBuffer();
        ddl.append("CREATE TABLE IF NOT EXISTS test_table (name string,age int,gender string)");
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        System.out.println(ddl.toString());
        stmt.execute(ddl.toString());
        closeResource(conn, stmt);
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<JsonObject> select() throws SQLException {
        List<JsonObject> resultList = new ArrayList<JsonObject>();
        StringBuffer ddl = new StringBuffer();
        ddl.append("SELECT * FROM test_table");
        Connection conn = dataSource.getConnection();
        System.out.println(ddl.toString());
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);
        ResultSet rs = stmt.executeQuery(ddl.toString());

        while (rs.next()) {
            resultList.add(getResultMap(rs));
        }
        conn.commit();
        conn.setAutoCommit(true);
        closeResource(conn, stmt);
        return resultList;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    private static JsonObject getResultMap(ResultSet rs) throws SQLException {
        JsonObject rawMap = new JsonObject();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();

        for (int i = 1; i <= count; i++) {
            String key = rsmd.getColumnLabel(i);
            Object value = rs.getObject(key);
            rawMap.addProperty(key, value.toString());
        }
        return rawMap;
    }

    /**
     * @param conn
     * @param stmt
     */
    private void closeResource(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
