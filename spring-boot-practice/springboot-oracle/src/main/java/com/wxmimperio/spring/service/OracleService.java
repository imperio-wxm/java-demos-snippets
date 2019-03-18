package com.wxmimperio.spring.service;

import com.google.common.collect.Lists;
import com.wxmimperio.spring.beans.Schema;
import com.wxmimperio.spring.common.SchemaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class OracleService {

    private DataSource phoenixDataSource;

    @Autowired
    public OracleService(DataSource phoenixDataSource) {
        this.phoenixDataSource = phoenixDataSource;
    }

    public List<Schema> getAllTables() {
        List<Schema> tablesName = Lists.newArrayList();
        try (Connection connection = phoenixDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "select t1.TABLE_NAME,t2.TABLE_TYPE,T2.COMMENTS from \n" +
                    "(select table_name TABLE_NAME from user_tables\n" +
                    "union all\n" +
                    "select view_name from user_views) t1 left join user_tab_comments t2 on t1.TABLE_NAME = t2.TABLE_NAME";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String tableType = resultSet.getString("TABLE_TYPE");
                String tableComment = resultSet.getString("COMMENTS");
                tablesName.add(new Schema(
                        tableName,
                        StringUtils.isEmpty(tableType) ? SchemaType.TABLE : SchemaType.valueOf(tableType.toUpperCase()),
                        StringUtils.isEmpty(tableComment) ? tableName : tableComment
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tablesName;
    }
}
