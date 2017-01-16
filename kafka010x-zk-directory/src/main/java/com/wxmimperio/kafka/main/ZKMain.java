package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.curator.CuratorConnectPool;
import com.wxmimperio.kafka.utils.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * Created by weiximing.imperio on 2017/1/16.
 */
public class ZKMain {

    public static void main(String args[]) {
        CuratorConnectPool curatorConnectPool = new CuratorConnectPool();
        CuratorFramework client = curatorConnectPool.getConnection();

        //获取根目录孩子节点
        getZookeeperRoot(client);

        curatorConnectPool.releaseConnection(client);
    }

    private static void getZookeeperRoot(CuratorFramework client) {
        String path = "/";
        List<String> rootC = CuratorUtils.getChildNode(client, path);
        System.out.println(rootC);
    }
}
