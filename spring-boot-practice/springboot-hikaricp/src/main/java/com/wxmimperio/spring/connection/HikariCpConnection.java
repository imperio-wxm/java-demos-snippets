package com.wxmimperio.spring.connection;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.wxmimperio.spring.config.HikaricpConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class HikariCpConnection {

    private HikariDataSource hikariDataSource;
    private HikaricpConfig hikaricpConfig;
    private MetricRegistry metricRegistry;
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public HikariCpConnection(HikaricpConfig hikaricpConfig) {
        this.hikaricpConfig = hikaricpConfig;
        this.metricRegistry = new MetricRegistry();
        this.healthCheckRegistry = new HealthCheckRegistry();
    }

    @Bean
    public MetricRegistry metrics() {
        return new MetricRegistry();
    }

    @Bean
    public HealthCheckRegistry healthCheck() {
        return new HealthCheckRegistry();
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
        this.hikariDataSource.setPoolName(hikaricpConfig.getPoolName());
        this.hikariDataSource.setMetricRegistry(metricRegistry);
        this.hikariDataSource.setHealthCheckRegistry(healthCheckRegistry);
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger("com.zaxxer.hikari.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(30, TimeUnit.SECONDS);
    }

    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public void checkHealth() {
        final Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
        for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
            if (entry.getValue().isHealthy()) {
                System.out.println(entry.getKey() + " is healthy");
            } else {
                System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
                final Throwable e = entry.getValue().getError();
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        this.hikariDataSource.close();
    }
}
