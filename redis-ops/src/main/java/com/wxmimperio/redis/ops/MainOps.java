package com.wxmimperio.redis.ops;

import com.wxmimperio.redis.connection.RedisConnection;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainOps {

    private static final String REDIS_NAMESPACE_HBASE_ALIAS = "hbase_alias";

    public static void main(String[] args) throws IOException {
        JedisCluster jedisCluster = RedisConnection.getInstance().getRedis();

        String alias = "";
        String table = alias + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd_HHmmss"));
        String key = REDIS_NAMESPACE_HBASE_ALIAS + ":" + alias;
        //set(jedisCluster, key, table);

        if (checkExists(jedisCluster, key)) {
            System.out.println(get(jedisCluster, key));
        }

        jedisCluster.close();
    }

    public static boolean checkExists(JedisCluster jedisCluster, String key) {
        return jedisCluster.exists(key);
    }

    public static void set(JedisCluster jedisCluster, String alias, String table) {
        jedisCluster.set(alias, table);
    }

    public static String get(JedisCluster jedisCluster, String alias) {
        return jedisCluster.get(alias);
    }
}
