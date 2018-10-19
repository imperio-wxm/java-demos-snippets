package com.wxmimperio.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.DropKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.DropTableSpecification;
import org.springframework.cassandra.core.keyspace.KeyspaceOption;
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
}
