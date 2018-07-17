package com.wxmimperio.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
public class HikaricpConfig {

    // JDBC driver
    private String dataSourceClassName;
    // url
    private String jdbcUrl;

    private String username;

    private String password;

    // This property controls the maximum amount of time that a connection is allowed to sit idle in the pool.
    // This setting only applies when minimumIdle is defined to be less than maximumPoolSize
    // Default: 600000 (10 minutes)
    private Long idleTimeout;

    // This property controls the maximum lifetime of a connection in the pool. An in-use connection will never be retired,
    // only when it is closed will it then be removed.
    // We strongly recommend setting this value, and it should be several seconds shorter than any database or infrastructure imposed connection time limit.
    // Default: 1800000 (30 minutes)
    private Long maxLifetime;

    // If your driver supports JDBC4 we strongly recommend not setting this property.
    // This is the query that will be executed just before a connection is given to you from the pool to validate that the connection to the database is still alive.
    private String connectionTestQuery;

    // This property controls the minimum number of idle connections that HikariCP tries to maintain in the pool.
    // However, for maximum performance and responsiveness to spike demands, we recommend not setting this value and instead allowing HikariCP to act as a fixed size connection pool.
    // Default: same as maximumPoolSize
    private Integer minimumIdle;

    // This property controls the maximum size that the pool is allowed to reach, including both idle and in-use connections.
    // Default: 10
    private Integer maximumPoolSize;


    // This property sets a SQL statement that will be executed after every new connection creation before adding it to the pool.
    private String connectionInitSql;

    // This property controls the maximum amount of time that a connection will be tested for aliveness.
    // This value must be less than the connectionTimeout
    // Default: 5000
    private Long validationTimeout;

    // This property controls the amount of time that a connection can be out of the pool before a message is logged indicating a possible connection leak.
    private Long leakDetectionThreshold;


    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public Integer getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(Integer minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public String getConnectionInitSql() {
        return connectionInitSql;
    }

    public void setConnectionInitSql(String connectionInitSql) {
        this.connectionInitSql = connectionInitSql;
    }

    public Long getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(Long validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    public Long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(Long leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }

    @Override
    public String toString() {
        return "HikaricpConfig{" +
                "dataSourceClassName='" + dataSourceClassName + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", idleTimeout=" + idleTimeout +
                ", maxLifetime=" + maxLifetime +
                ", connectionTestQuery='" + connectionTestQuery + '\'' +
                ", minimumIdle=" + minimumIdle +
                ", maximumPoolSize=" + maximumPoolSize +
                ", connectionInitSql='" + connectionInitSql + '\'' +
                ", validationTimeout=" + validationTimeout +
                ", leakDetectionThreshold=" + leakDetectionThreshold +
                '}';
    }
}
