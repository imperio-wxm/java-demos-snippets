package com.wxmimperio.presto.pool;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.wxmimperio.presto.simple.SimpleConnect;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PrestoConnectionPool {
    private final static Logger LOG = LoggerFactory.getLogger(SimpleConnect.class);

    private HikariDataSource hikariDataSource;
    private HealthCheckRegistry healthCheckRegistry;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private PrestoConnectionPool() {
        initPool();
    }

    private static class SingletonInstance {
        private static final PrestoConnectionPool INSTANCE = new PrestoConnectionPool();
    }

    public static PrestoConnectionPool getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    private void initPool() {
        Properties props = new Properties();
        MetricRegistry metricRegistry = new MetricRegistry();
        try (InputStream is = this.getClass().getResourceAsStream("/dbpool.properties")) {
            props.load(is);
            LOG.info("add properties = " + props);
        } catch (IOException e) {
            LOG.error("Can not get properties.", e);
        }
        this.healthCheckRegistry = new HealthCheckRegistry();
        this.hikariDataSource = new HikariDataSource();
        this.hikariDataSource.setDriverClassName(props.getProperty("driverClassName"));
        this.hikariDataSource.setJdbcUrl(props.getProperty("url"));
        this.hikariDataSource.setUsername(props.getProperty("username"));
        this.hikariDataSource.setPassword(props.getProperty("password"));
        this.hikariDataSource.setIdleTimeout(Long.parseLong(props.getProperty("idleTimeout")));
        this.hikariDataSource.setMaxLifetime(Long.parseLong(props.getProperty("maxLifetime")));
        //this.hikariDataSource.setConnectionTestQuery(props.getProperty("connectionTestQuery"));
        this.hikariDataSource.setMinimumIdle(Integer.parseInt(props.getProperty("minimumIdle")));
        this.hikariDataSource.setMaximumPoolSize(Integer.parseInt(props.getProperty("maxConn")));
        this.hikariDataSource.setConnectionInitSql(props.getProperty("connectionInitSql"));
        this.hikariDataSource.setValidationTimeout(Integer.parseInt(props.getProperty("validationTimeout")));
        this.hikariDataSource.setLeakDetectionThreshold(Long.parseLong(props.getProperty("leakDetectionThreshold")));
        this.hikariDataSource.setPoolName(props.getProperty("poolName"));
        this.hikariDataSource.setMetricRegistry(metricRegistry);
        this.hikariDataSource.setHealthCheckRegistry(healthCheckRegistry);
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger(PrestoConnectionPool.class))
                .withLoggingLevel(Slf4jReporter.LoggingLevel.INFO)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(10, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(checkHealth, 2, 5, TimeUnit.MINUTES);
    }

    private Runnable checkHealth = () -> {
        final Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
        for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
            if (entry.getValue().isHealthy()) {
                LOG.info(entry.getKey() + " is healthy");
            } else {
                LOG.error(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
                final Throwable e = entry.getValue().getError();
                if (e != null) {
                    throw new RuntimeException("Presto connection pool is not health.", e);
                }
            }
        }
    };

    public void closePool() {
        this.hikariDataSource.close();
        this.service.shutdown();
    }
}
