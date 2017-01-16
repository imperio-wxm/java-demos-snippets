package com.wxmimperio.kafka.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wxmimperio on 2016/12/3.
 */
public class CuratorUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CuratorUtils.class);

    private static final byte[] nullDate = "".getBytes();

    public CuratorUtils() {
    }

    public static String readDataMaybeNull(CuratorFramework client, String path) {
        byte[] data = null;
        try {
            if (client.checkExists().forPath(path) != null) {
                data = client.getData().forPath(path);
            } else {
                LOG.info(path + " is not exist!");
            }
        } catch (Exception e) {
            LOG.error("读取" + path + "数据失败 " + e);
        }
        return new String(data != null ? data : nullDate);
    }

    public static List<String> getChildNode(CuratorFramework client, String path) {
        List<String> node = new ArrayList<String>();
        try {
            if (client.checkExists().forPath(path) != null) {
                node = client.getChildren().forPath(path);
            }
        } catch (Exception e) {
            LOG.error("获取子节点" + path + "失败 " + e);
        }
        return node;
    }

    public static Map<String, Object> readDataStatMaybeNull(CuratorFramework client, String path) {
        Map<String, Object> dataAndStat = new ConcurrentHashMap<>();
        byte[] data;
        Stat stat;
        try {
            stat = client.checkExists().forPath(path);
            if (stat != null) {
                data = client.getData().forPath(path);
                dataAndStat.put("data", new String(data != null ? data : nullDate));
                dataAndStat.put("stat", stat);
            } else {
                LOG.info(path + " is not exist!");
            }
        } catch (Exception e) {
            LOG.error("获取节点" + path + "数据失败 " + e);
        }
        return dataAndStat;
    }

    public static Map<String, Object> getChildNodeStat(CuratorFramework client, String path) {
        Map<String, Object> childNodeAndStat = new ConcurrentHashMap<>();
        Stat stat;
        try {
            stat = client.checkExists().forPath(path);
            if (stat != null) {
                childNodeAndStat.put("childNodes", client.getChildren().forPath(path));
                childNodeAndStat.put("stat", stat);
            } else {
                LOG.info(path + " is not exist!");
            }
        } catch (Exception e) {
            LOG.error("获取子节点" + path + "失败 " + e);
        }
        return childNodeAndStat;
    }
}
