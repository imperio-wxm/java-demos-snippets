package com.wxmimperio.redis;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.Objects;

public class JedisMain {

    private static Jedis jedis;
    private static HostAndPort hostAndPort;

    static {
        initClient();
    }

    public static void main(String[] args) throws Exception {
        /*GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(1);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(5000);
        //初始化Jedis连接池，通常来讲JedisPool是单例的
        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        for (int i = 0; i < 10; i++) {
            jedis = jedisPool.getResource();
            System.out.println(jedis.isConnected());
            jedis.connect();
            Client client = jedis.getClient();
            String host = client.getHost();
            int port = client.getPort();
        }*/

      /*  jedis.set("hello", "world");
        //3.jedis执行get操作，value="world"
        String value = jedis.get("hello");
        System.out.println(value);
        System.out.println(hostAndPort);*/
        int i = 0;
        while (true) {
            try {
                if (validateObject(jedis, hostAndPort)) {
                    System.out.println("连接正常...");
                } else {
                    if (reConnect(10)) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                i++;
                Thread.sleep(2000);
            }
        }
    }

    public static boolean reConnect(int maxTry) throws Exception {
        boolean result = true;
        int times = 0;
        while (times < maxTry) {
            if (validateObject(jedis, hostAndPort)) {
                result = false;
                break;
            } else {
                try {
                    initClient();
                } catch (final Exception ignored) {
                }
            }
            times++;
            System.out.println(String.format("第 %s 次重连...", times));
            Thread.sleep(2000);
        }
        return result;
    }

    public static boolean validateObject(Jedis jedis, HostAndPort hostAndPort) {
        try {
            String connectionHost = jedis.getClient().getHost();
            int connectionPort = jedis.getClient().getPort();
            return hostAndPort.getHost().equals(connectionHost) && hostAndPort.getPort() == connectionPort
                    && jedis.isConnected() && jedis.ping().equals("PONG") && !jedis.getClient().isBroken();
        } catch (final Exception e) {
            return false;
        }
    }

    public static Jedis getNewClient() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.ping();
        return jedis;
    }

    public static void initClient() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
        }
        jedis = getNewClient();
        hostAndPort = new HostAndPort(jedis.getClient().getHost(), jedis.getClient().getPort());
    }
}
