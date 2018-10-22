package com.wxmimperio.spring.service;

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
