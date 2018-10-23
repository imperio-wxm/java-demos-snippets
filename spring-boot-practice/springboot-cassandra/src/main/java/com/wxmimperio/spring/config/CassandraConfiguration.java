package com.wxmimperio.spring.config;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories
@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    private CassandraConfig cassandraConfig;

    @Autowired
    public CassandraConfiguration(CassandraConfig cassandraConfig) {
        this.cassandraConfig = cassandraConfig;
    }

    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        QueryOptions options = new QueryOptions();
        options.setConsistencyLevel(ConsistencyLevel.QUORUM);
        cluster.setContactPoints(cassandraConfig.getContactPoints());
        cluster.setPort(cassandraConfig.getPort());
        cluster.setJmxReportingEnabled(true);
        cluster.setMetricsEnabled(true);
        cluster.setQueryOptions(options);
        return cluster;
    }

    @Override
    protected String getKeyspaceName() {
        return cassandraConfig.getKeyspace();
    }

    @Override
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
}
