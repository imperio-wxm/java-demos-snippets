package com.wxmimperio.spring.connection;

import com.codahale.metrics.MetricRegistry;
import com.wxmimperio.spring.config.HikaricpConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HikariCpConnection {

    private HikariDataSource hikariDataSource;
    private HikaricpConfig hikaricpConfig;

    @Autowired
    public HikariCpConnection(HikaricpConfig hikaricpConfig) {
        this.hikaricpConfig = hikaricpConfig;
    }

    @Bean
    public MetricRegistry metrics() {
        return new MetricRegistry();
    }

    @PostConstruct
    private void hikariDataSource() {
        System.out.println(hikaricpConfig);
        this.hikariDataSource = new HikariDataSource();
        this.hikariDataSource.setDriverClassName(hikaricpConfig.getDataSourceClassName());
        this.hikariDataSource.setJdbcUrl(hikaricpConfig.getJdbcUrl());
        this.hikariDataSource.setUsername(hikaricpConfig.getUsername());
        this.hikariDataSource.setPassword(hikaricpConfig.getPassword());
        this.hikariDataSource.setIdleTimeout(hikaricpConfig.getIdleTimeout());
        this.hikariDataSource.setMaxLifetime(hikaricpConfig.getMaxLifetime());
        this.hikariDataSource.setConnectionTestQuery(hikaricpConfig.getConnectionTestQuery());
        this.hikariDataSource.setMinimumIdle(hikaricpConfig.getMinimumIdle());
        this.hikariDataSource.setMaximumPoolSize(hikaricpConfig.getMaximumPoolSize());
        this.hikariDataSource.setConnectionInitSql(hikaricpConfig.getConnectionInitSql());
        this.hikariDataSource.setValidationTimeout(hikaricpConfig.getValidationTimeout());
        this.hikariDataSource.setLeakDetectionThreshold(hikaricpConfig.getLeakDetectionThreshold());
        this.hikariDataSource.setMetricRegistry(metrics());
    }

    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public void close() {
        this.hikariDataSource.close();
    }
}
