package com.wxmimperio.spring.service;

import com.datastax.driver.core.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
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
}
