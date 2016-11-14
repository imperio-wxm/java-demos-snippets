package com.wxmimperio.curator.connect;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorConnect {
    private static final String ZK_ADDRESS = "192.168.18.74:2181";
    private CuratorFramework curatorClient;

    public CuratorConnect() {
        this.curatorClient = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS)
                .connectionTimeoutMs(5000)   //连接超时时间
                .sessionTimeoutMs(3000)      //会话超时时间
                .retryPolicy(new RetryNTimes(5, 5000))
                .build();
    }

    public CuratorFramework getCuratorConnect() {
        this.curatorClient.start();
        return this.curatorClient;
    }

    public void closeCuratorConnect(CuratorFramework curatorClient) {
        if (curatorClient != null) {
            curatorClient.close();
        }
    }
}
