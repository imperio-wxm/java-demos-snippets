package com.wxmimperio.spring.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "spring.datasource.hikari")
@Component
public class OracleConfig  extends HikariConfig {

    private static Logger LOG = LoggerFactory.getLogger(OracleConfig.class);
    private static final long DEFAULT_CHECKHEALTH_PURGER_INTERVAL = 60 * 1000 * 30L;

    private MetricRegistry metricRegistry;
    private HealthCheckRegistry healthCheckRegistry;

    public OracleConfig() {
        this.metricRegistry = new MetricRegistry();
        this.healthCheckRegistry = new HealthCheckRegistry();
    }

    @Bean
    public DataSource oracleDataSource() {
        setMetricRegistry(metricRegistry);
        setHealthCheckRegistry(healthCheckRegistry);
        HikariDataSource hikariDataSource = new HikariDataSource(this);
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger("com.zaxxer.hikari.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.MINUTES);
        return hikariDataSource;
    }

    @Scheduled(fixedRate = DEFAULT_CHECKHEALTH_PURGER_INTERVAL)
    public void checkHealth() {
        final Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
        for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
            if (entry.getValue().isHealthy()) {
                LOG.info(entry.getKey() + " is healthy");
            } else {
                LOG.error(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
                final Throwable e = entry.getValue().getError();
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
    }
}
