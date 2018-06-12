package com.wxmimperio.redis.connection;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class RedisConnection {
    private static final int DEFAULT_TIMEOUT = 2000;
    private static final int DEFAULT_MAX_REDIRECTIONS = 5;
    private JedisCluster redis = null;

    private volatile static RedisConnection inst;

    RedisConnection() {
        ResourceBundle rb = ResourceBundle.getBundle("application", Locale.getDefault());
        String url = rb.getString("redis.url");
        String password = rb.getString("redis.password").equalsIgnoreCase("") ? null : rb.getString("redis.password");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String from : url.split(",")) {
            nodes.add(HostAndPort.parseString(from));
        }
        redis = new JedisCluster(nodes, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_MAX_REDIRECTIONS, password, new JedisPoolConfig());
    }

    public static RedisConnection getInstance() {
        if (inst == null) {
            synchronized (RedisConnection.class) {
                if (inst == null) {
                    inst = new RedisConnection();
                }
            }
        }
        return inst;
    }

    public JedisCluster getRedis() {
        return redis;
    }
}
