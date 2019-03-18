package com.wxmimperio.spring.service;

import com.google.common.collect.Lists;
import com.wxmimperio.spring.beans.Column;
import com.wxmimperio.spring.beans.Schema;
import com.wxmimperio.spring.common.DataType;
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
    private static final List<String> filter = Lists.newArrayList("SYS_EXPORT_SCHEMA_01");

    @Autowired
    public OracleService(DataSource phoenixDataSource) {
        this.phoenixDataSource = phoenixDataSource;
    }

    public List<Schema> getAllTables() {
        List<Schema> tables = Lists.newArrayList();
        try (Connection connection = phoenixDataSource.getConnection();
             Statement statement = connection.createStatement();
             Statement colStatement = connection.createStatement()) {
            String sql = "select t1.TABLE_NAME,t2.TABLE_TYPE,T2.COMMENTS from \n" +
                    "(select table_name TABLE_NAME from user_tables\n" +
                    "union all\n" +
                    "select view_name from user_views) t1 left join user_tab_comments t2 on t1.TABLE_NAME = t2.TABLE_NAME";

            ResultSet resultSet = statement.executeQuery(sql);

            int count = 0;

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");

                if (!filter.contains(tableName)) {
                    String tableType = resultSet.getString("TABLE_TYPE");
                    String tableComment = resultSet.getString("COMMENTS");

                    Schema schema = new Schema(
                            tableName,
                            StringUtils.isEmpty(tableType) ? SchemaType.TABLE : SchemaType.valueOf(tableType.toUpperCase()),
                            StringUtils.isEmpty(tableComment) ? tableName : tableComment
                    );

                    String colSql = "SELECT b.TABLE_NAME,b.column_name,b.data_type,b.data_precision,b.data_scale,b.column_id,b.char_length,a.comments FROM  USER_TAB_COLUMNS b,USER_COL_COMMENTS a WHERE b.TABLE_NAME = '" + tableName + "' AND b.TABLE_NAME = a.TABLE_NAME AND b.COLUMN_NAME = a.COLUMN_NAME";

                    ResultSet colRs = colStatement.executeQuery(colSql);
                    List<Column> columns = Lists.newArrayList();
                    while (colRs.next()) {
                        String colName = colRs.getString("COLUMN_NAME");


                        String dataType = colRs.getString("DATA_TYPE");
                        int dataPrecision = colRs.getInt("DATA_PRECISION");
                        int dataScale = colRs.getInt("DATA_SCALE");
                        int charLength = colRs.getInt("CHAR_LENGTH");
                        String comment = colRs.getString("COMMENTS");

                        DataType realDataType = null;
                        try {
                            for (DataType dt : DataType.values()) {
                                if (dataType.startsWith(dt.name())) {
                                    realDataType = dt;
                                    break;
                                }
                            }

                            if (realDataType == null) {
                                System.exit(1);
                            }

                            if (realDataType.equals(DataType.NUMBER)) {
                                if (dataPrecision == 0 && dataScale == 0) {
                                    realDataType = DataType.INTEGER;
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            System.out.println("error table = " + tableName);
                        }
                        columns.add(new Column(colName, realDataType, comment));
                    }
                    schema.setColumns(columns);
                    tables.add(schema);
                    count++;
                    System.out.println("table count = " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

}
