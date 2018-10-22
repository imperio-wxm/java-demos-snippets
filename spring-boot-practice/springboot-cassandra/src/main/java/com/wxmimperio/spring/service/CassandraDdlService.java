package com.wxmimperio.spring.service;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.wxmimperio.spring.common.CassandraDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.AlterTableSpecification;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.DropKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.DropTableSpecification;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        DropTableSpecification dropTableSpecification = new DropTableSpecification();
        dropTableSpecification.name(tableName);
        cassandraTemplate.execute(dropTableSpecification);
    }

    @Transactional(rollbackFor = Throwable.class)
    public String dropTable(String keySpace, String tableName) {
        StringBuilder cql = new StringBuilder();
        cql.append("DROP TABLE ").append(keySpace).append(".").append(tableName);
        cassandraTemplate.execute(cql.toString());
        return cql.toString();
    }

    @Transactional(rollbackFor = Throwable.class)
    public String createKeyspace(String keySpace) {
        CreateKeyspaceSpecification keyspaceSpecification = new CreateKeyspaceSpecification();
        keyspaceSpecification.name(keySpace).withNetworkReplication(DataCenterReplication.dcr("dc1", 2));
        cassandraTemplate.execute(keyspaceSpecification);
        return keySpace;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String dropKeyspace(String keySpace) {
        DropKeyspaceSpecification dropKeyspaceSpecification = new DropKeyspaceSpecification();
        dropKeyspaceSpecification.name(keySpace);
        cassandraTemplate.execute(dropKeyspaceSpecification);
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
        AlterTableSpecification alterTableSpecification = new AlterTableSpecification();
        alterTableSpecification.name(tableName).rename(oldName, newName);
        cassandraTemplate.execute(alterTableSpecification);
        return newName;
    }

    @Transactional(rollbackFor = Throwable.class)
    public String addColumn(String keyspace, String tableName, String colName, CassandraDataType cassandraDataType) {
        String useCql = "USE " + keyspace;
        cassandraTemplate.execute(useCql);
        AlterTableSpecification alterTableSpecification = new AlterTableSpecification();
        alterTableSpecification.name(tableName).add(colName, getDataTypeByName(cassandraDataType));
        cassandraTemplate.execute(alterTableSpecification);
        return colName;
    }

    private DataType getDataTypeByName(CassandraDataType cassandraDataType) {
        switch (cassandraDataType) {
            case DATE:
                return DataType.date();
            case TIMESTAMP:
                return DataType.timestamp();
            case BOOLEAN:
                return DataType.cboolean();
            case DOUBLE:
                return DataType.cdouble();
            case FLOAT:
                return DataType.cfloat();
            case INT:
                return DataType.cint();
            case BIGINT:
                return DataType.bigint();
            case TEXT:
                return DataType.text();
            case TIME:
                return DataType.time();
            case UUID:
                return DataType.uuid();
            case INET:
                return DataType.inet();
            case ASCII:
                return DataType.ascii();
            case DECIMAL:
                return DataType.decimal();
            case VARCHAR:
                return DataType.varchar();
            case VARINT:
                return DataType.varint();
            case TIMEUUID:
                return DataType.timeuuid();
            case TINYINT:
                return DataType.tinyint();
            case SMALLINT:
                return DataType.smallint();
            case COUNTER:
                return DataType.counter();
            default:
                throw new RuntimeException("Can not get this type = " + cassandraDataType);
        }
    }

   /*private static ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new CleanWork());
    }

    static class CleanWork extends Thread {
        @Override
        public void run() {
            executorService.shutdown();
            try {
                while (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {

                }
            } catch (Exception e) {

            }
        }
    }*/
}
