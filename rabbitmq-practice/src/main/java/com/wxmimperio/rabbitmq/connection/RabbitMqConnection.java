package com.wxmimperio.rabbitmq.connection;

import com.google.common.collect.Maps;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMqConnection {

    private static Map<String, Connection> connectionFactoryMap = Maps.newHashMap();
    private String connectName = "wxm_default_connection";
    private ConnectionFactory factory = new ConnectionFactory();

    public RabbitMqConnection() {
        initConnectionFactory();
    }

    public RabbitMqConnection(String connectName) {
        this.connectName = connectName;
        initConnectionFactory();
    }

    public Connection getConnection() throws IOException, TimeoutException {
        if (connectionFactoryMap.containsKey(connectName)) {
            return connectionFactoryMap.get(connectName);
        } else {
            return initConnection();
        }
    }

    private void initConnectionFactory() {
        factory.setAutomaticRecoveryEnabled(true);
        factory.setHost("10.174.20.24");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("sd309klH3owW28XApq");
    }

    private Connection initConnection() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        connectionFactoryMap.put(connectName, connection);
        return connection;
    }

    public void closeAllConnection() {
        connectionFactoryMap.forEach((name, connection) -> {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
