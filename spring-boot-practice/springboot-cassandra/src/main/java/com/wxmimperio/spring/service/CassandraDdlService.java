package com.wxmimperio.spring.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wxmimperio.spring.common.CassandraDataType;
import com.wxmimperio.spring.common.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.*;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class CassandraDdlService {

    private CassandraOperations cassandraTemplate;

    @Autowired
    public CassandraDdlService(CassandraOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void createTable(String cql) {
        cassandraTemplate.execute(cql);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void dropDefaultKeyspaceTable(String tableName) {
        cassandraTemplate.execute(new DropTableSpecification().name(tableName));
    }

    @Transactional(rollbackFor = Throwable.class)
    public String dropTable(String keySpace, String tableName) {
        String useCql = "USE " + keySpace;
        cassandraTemplate.execute(useCql);
        cassandraTemplate.execute(new DropTableSpecification().name(tableName));
        return keySpace;
    }

    @Transactional(rollbackFor = Throwable.class)
    public List<String> listKeyspaces() {
        List<String> keyspaces = new ArrayList<>();
        String dbCql = "select * from system_schema.keyspaces";
        ResultSet resultSet = cassandraTemplate.query(dbCql);
        resultSet.forEach(row -> {
            keyspaces.add(row.getString("keyspace_name"));
        });
        return keyspaces;
    }

    @Transactional(rollbackFor = Throwable.class)
    public List<String> listKeyspaceTables(String keyspace) {
        List<String> tables = new ArrayList<>();
        String cql = "select * from system_schema.tables where keyspace_name = '" + keyspace + "'";
        ResultSet resultSet = cassandraTemplate.query(cql);
        resultSet.forEach(row -> tables.add(row.getString("table_name")));
        return tables;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String createKeyspace(String keySpace) {
        cassandraTemplate.execute(new CreateKeyspaceSpecification()
                .name(keySpace)
                .withNetworkReplication(DataCenterReplication.dcr("dc1", 2))
        );
        return keySpace;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String dropKeyspace(String keySpace) {
        cassandraTemplate.execute(new DropKeyspaceSpecification().name(keySpace));
        return keySpace;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String truncateTable(String keyspace, String tableName) {
        cassandraTemplate.execute(QueryBuilder.truncate(keyspace, tableName));
        return keyspace + ":" + tableName;
    }

    /**
     * Just can rename primary key
     *
     * @param keyspace
     * @param tableName
     * @param oldName
     * @param newName
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public String renameColumn(String keyspace, String tableName, String oldName, String newName) {
        String useCql = "USE " + keyspace;
        cassandraTemplate.execute(useCql);
        cassandraTemplate.execute(new AlterTableSpecification().name(tableName).rename(oldName, newName));
        return newName;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String addColumn(String keyspace, String tableName, String colName, CassandraDataType cassandraDataType) {
        String useCql = "USE " + keyspace;
        cassandraTemplate.execute(useCql);
        cassandraTemplate.execute(new AlterTableSpecification().name(tableName).add(colName, CassandraDataType.getDataTypeByName(cassandraDataType)));
        return colName;
    }

    /**
     * CQL data types have strict requirements for conversion compatibility. The following table shows the allowed alterations for data types:
     * ======================================================
     * Data type may be altered to                | Data type
     * ------------------------------------------------------
     * ascii, bigint, boolean, decimal,           |
     * double, float, inet, int, timestamp,       | blob
     * timeuuid, uuid, varchar, varint	           |
     * ------------------------------------------------------
     * int	                                       | varint
     * ------------------------------------------------------
     * text	                                   | varchar
     * ------------------------------------------------------
     * timeuuid	                               | uuid
     * ------------------------------------------------------
     * varchar	                                   | text
     * ======================================================
     * <p>
     * Clustering columns have even stricter requirements, because clustering columns mandate the order in which data is written to disk.
     * The following table shows the allow alterations for data types used in clustering columns:
     * ===========================================
     * Data type may be altered to   | Data type
     * -------------------------------------------
     * int	                          | varint
     * -------------------------------------------
     * text	                      | varchar
     * -------------------------------------------
     * varchar	                      | text
     * ===========================================
     *
     * @param keyspace
     * @param tableName
     * @param colName
     * @param newType
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public String changeColumnType(String keyspace, String tableName, String colName, CassandraDataType newType) {
        String useCql = "USE " + keyspace;
        cassandraTemplate.execute(useCql);
        cassandraTemplate.execute(new AlterTableSpecification().name(tableName).alter(colName, CassandraDataType.getDataTypeByName(newType)));
        return colName;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String dropColumn(String keyspace, String tableName, String colName) {
        String useCql = "USE " + keyspace;
        cassandraTemplate.execute(useCql);
        cassandraTemplate.execute(new AlterTableSpecification().name(tableName).drop(colName));
        return colName;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String getTableColumns(String keyspace, String tableName) {
        String descCql = "select * from system_schema.columns where keyspace_name='" + keyspace +
                "' and table_name='" + tableName + "'";
        ResultSet resultSet = cassandraTemplate.query(descCql);
        Map<Integer, JSONObject> partitionKeys = Maps.newHashMap();
        Map<Integer, JSONObject> clusterKeys = Maps.newHashMap();
        List<JSONObject> columns = Lists.newArrayList();
        List<JSONObject> partitionColumns = Lists.newArrayList();
        List<JSONObject> clusterColumns = Lists.newArrayList();
        resultSet.forEach(row -> {
            JSONObject col = new JSONObject();
            col.put("colName", row.getString("column_name"));
            col.put("type", row.getString("type"));
            col.put("position", row.getInt("position"));
            switch (row.getString("kind")) {
                case "partition_key":
                    partitionKeys.put(row.getInt("position"), col);
                    break;
                case "clustering":
                    clusterKeys.put(row.getInt("position"), col);
                    break;
                default:
                    columns.add(col);
            }
        });
        sortMap(partitionKeys).forEach(pk -> partitionColumns.add(pk.getValue()));
        sortMap(clusterKeys).forEach(ck -> clusterColumns.add(ck.getValue()));
        JSONObject result = new JSONObject();
        result.put("tableName", tableName);
        result.put("keySpace", keyspace);
        result.put("columns", columns);
        result.put("partitionKeys", partitionColumns);
        result.put("clusterKeys", clusterColumns);
        return result.toJSONString();
    }

    private List<Map.Entry<Integer, JSONObject>> sortMap(Map<Integer, JSONObject> map) {
        List<Map.Entry<Integer, JSONObject>> list = Lists.newArrayList(map.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getKey));
        return list;
    }


    /**
     * Lots of configs in https://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlCreateTable.html#cqlCreateTable
     *
     * @param keyspace
     * @param tableName
     */
    @Transactional(rollbackFor = Throwable.class)
    public void alterTableOptions(String keyspace, String tableName) {
        String useCql = "USE " + keyspace;
        cassandraTemplate.execute(useCql);
        AlterTableSpecification alterTableSpecification = new AlterTableSpecification().name(tableName);
        alterTableSpecification.with(TableOption.COMMENT, "测试表");
        Map<Option, Object> map = new HashMap<>(1);
        map.put(TableOption.CompressionOption.SSTABLE_COMPRESSION, "org.apache.cassandra.io.compress.LZ4Compressor");
        alterTableSpecification.with(TableOption.COMPRESSION, map);
        cassandraTemplate.execute(alterTableSpecification);
    }

    public String getTableDetails(String keyspace, String tableName) {
        String tableDetailCql = "select bloom_filter_fp_chance,caching,compaction,compression,default_time_to_live,gc_grace_seconds,id,max_index_interval,min_index_interval" +
                " from system_schema.tables where keyspace_name='" + keyspace +
                "' and table_name='" + tableName + "'";
        JSONObject jsonObject = new JSONObject(true);
        ResultSet resultSet = cassandraTemplate.query(tableDetailCql);
        resultSet.forEach(row -> {
            ColumnDefinitions columnDefinitions = row.getColumnDefinitions();
            columnDefinitions.forEach(definition -> {
                String colName = definition.getName();
                Object colValue = row.getObject(colName);
                jsonObject.put(StringUtil.underline2Camel(colName, true), colValue);
            });
        });
        System.out.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
