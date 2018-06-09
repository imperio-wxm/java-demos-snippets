package com.wxmimperio.serializable;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.sql.*;

public class MySqlOps implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db?serverTimezone=GMT";
    private static final String USER = "root";
    private static final String PASS = "password";
    private Connection connection;

    public MySqlOps() {

    }

    public void initConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        if (connection == null) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
    }

    public void selectSql(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    jsonObject.addProperty(columnName, rs.getString(columnName));
                }
                System.out.println(jsonObject);
            }
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}
