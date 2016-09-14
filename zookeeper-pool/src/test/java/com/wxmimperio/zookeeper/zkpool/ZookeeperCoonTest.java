package com.wxmimperio.zookeeper.zkpool;

import com.wxmimperio.zookeeper.quartz.QuartzUtil;
import com.wxmimperio.zookeeper.quartz.QuartzZKGetTopics;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

/**
 * Created by weiximing.imperio on 2016/9/14.
 */
public class ZookeeperCoonTest {


    public static void main(String args[]) {
        ZookeeperCoonTest zookeeperCoonTest = new ZookeeperCoonTest();
        zookeeperCoonTest.getChildren();
    }

    private void getChildren() {
        QuartzUtil quartzUtil = QuartzUtil.getInstance("Job_Group", "Trigger_Group");

        JobDataMap job1Map = new JobDataMap();
        job1Map.put("name", "job1");

        JobDataMap job2Map = new JobDataMap();
        job2Map.put("name", "job2");

        try {
            quartzUtil.addJob(
                    "get_topic_job",
                    "get_topic_trigger",
                    QuartzZKGetTopics.class,
                    "*/5 * * * * ?",
                    job1Map
            );

            quartzUtil.addJob(
                    "get_topic_job2",
                    "get_topic_trigger2",
                    QuartzZKGetTopics.class,
                    "*/3 * * * * ?",
                    job2Map
            );
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getObjNum() {
        ZookeeperConnPool zookeeperConnPool = ZookeeperConnPool.getInstance();

        for (int i = 0; i < 5; i++) {
            ZkClient zkClient  = zookeeperConnPool.getConnection();
            System.out.println("========" + zkClient.toString());
            System.out.println("++++++" + zookeeperConnPool.getActive());
            //zookeeperConnPool.closeConnection(zkClient);
            zookeeperConnPool.releaseConnection(zkClient);
        }
    }
}
