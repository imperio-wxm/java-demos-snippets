package com.wxmimperio.zookeeper.zkpool;

import com.wxmimperio.zookeeper.quartz.QuartzUtil;
import com.wxmimperio.zookeeper.quartz.QuartzZKGetTopics;
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
        try {
            quartzUtil.addJob(
                    "get_topic_job",
                    "get_topic_trigger",
                    QuartzZKGetTopics.class,
                    "*/5 * * * * ?",
                    null
            );
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
