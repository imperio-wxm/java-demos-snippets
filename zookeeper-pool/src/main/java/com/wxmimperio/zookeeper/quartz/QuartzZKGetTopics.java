package com.wxmimperio.zookeeper.quartz;

import com.wxmimperio.zookeeper.zkpool.ZookeeperConnPool;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/14.
 */
public class QuartzZKGetTopics implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzZKGetTopics.class);

    //提前创建文件
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        ZookeeperConnPool zookeeperConnPool = ZookeeperConnPool.getInstance();
        ZkClient zkClient = zookeeperConnPool.getConnection();
        LOG.info("Get zk = " + zkClient.toString() + " Job Key = " + jobKey);

        List<String> newTopics = zkClient.getChildren("/brokers/topics");

        System.out.println("=============");
        for (String topic : newTopics) {
            System.out.println("\t" + topic);
        }
        System.out.println("=============");
        zookeeperConnPool.releaseConnection(zkClient);
    }
}
