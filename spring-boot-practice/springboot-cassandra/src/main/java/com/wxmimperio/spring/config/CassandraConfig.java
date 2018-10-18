package com.wxmimperio.spring.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cassandra")
@Component
public class CassandraConfig {

    private String contactPoints;
    private Integer port;
    private String keyspace;

    public String getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(String contactPoints) {
        this.contactPoints = contactPoints;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }

    @Override
    public String toString() {
        return "CassandraConfig{" +
                "contactPoints='" + contactPoints + '\'' +
                ", port=" + port +
                ", keyspace='" + keyspace + '\'' +
                '}';
    }
}
