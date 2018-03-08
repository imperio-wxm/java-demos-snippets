package com.wxmimperio.es;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class EsJdbcTest {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("url", "");
        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        Connection connection = dds.getConnection();
        PreparedStatement ps = connection.prepareStatement("");
        ResultSet resultSet = ps.executeQuery();
        ResultSetMetaData meta = resultSet.getMetaData();
        System.out.println(meta.getColumnCount() + "=========");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("area_id"));
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String colName = meta.getColumnName(i);
                System.out.println(colName + " = " + resultSet.getObject(colName));
            }
        }
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet res = databaseMetaData.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});
        List<String> tables = Lists.newArrayList();
        while (res.next()) {
            String tableName = res.getString("TABLE_NAME");
            tables.add(tableName);
            System.out.println(tableName);
        }
        System.out.println(tables);
        ps.close();
        connection.close();
        dds.close();
    }

}
