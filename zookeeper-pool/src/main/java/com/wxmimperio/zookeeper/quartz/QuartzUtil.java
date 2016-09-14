package com.wxmimperio.zookeeper.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2016/8/3.
 */
public class QuartzUtil {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzUtil.class);
    private final String jobGroupName;
    private final String triggerGroupName;
    private Scheduler scheduler;
    private final static SchedulerFactory sf = new StdSchedulerFactory();
    private final static Map<String, QuartzUtil> instanceMap = new ConcurrentHashMap<String, QuartzUtil>();
    private final static Map<String, Scheduler> schedulerMap = new ConcurrentHashMap<String, Scheduler>();

    //构造单例
    private QuartzUtil(String jobGroupName, String triggerGroupName) {
        this.jobGroupName = jobGroupName;
        this.triggerGroupName = triggerGroupName;
    }

    /**
     * 构造单例，并加入map
     *
     * @param jobGroupName
     * @param triggerGroupName
     * @return
     */
    public synchronized static QuartzUtil getInstance(String jobGroupName, String triggerGroupName) {
        String instanceKey = jobGroupName + "_" + triggerGroupName;
        if (instanceMap.containsKey(instanceKey)) {
            return instanceMap.get(instanceKey);
        }
        QuartzUtil quartzUtil = new QuartzUtil(jobGroupName, triggerGroupName);
        instanceMap.put(instanceKey, quartzUtil);
        return quartzUtil;
    }

    /**
     * 获取实例
     *
     * @return
     * @throws SchedulerException
     */
    private Scheduler getScheduler() throws SchedulerException {
        if (scheduler == null) {
            scheduler = sf.getScheduler();
        }
        return scheduler;
    }

    /**
     * 添加job
     *
     * @param jobName
     * @param triggerName
     * @param cls
     * @param cronSchedule
     * @param jobBindData
     * @throws SchedulerException
     */
    public void addJob(String jobName, String triggerName, Class cls,
                       String cronSchedule, Map<String, Object> jobBindData) throws SchedulerException {

        //设置执行时间，从当前的下一秒开始
        Date runTime = DateBuilder.evenSecondDateAfterNow();

        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();

        if (CronExpression.isValidExpression(cronSchedule)) {
            //添加绑定数据，可以为null
            if (jobBindData != null) {
                for (Map.Entry<String, Object> entry : jobBindData.entrySet()) {
                    jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
                }
            }

            //创建触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .startAt(runTime)
                    //.startNow()
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(cronSchedule)
                    ).build();

            Scheduler scheduler = getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);

            //将计划加入map
            String schedulerKey = jobGroupName + "<" + jobName + ">" + "-" + triggerGroupName + "<" + triggerName + ">";
            schedulerMap.put(schedulerKey, scheduler);

            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } else {
            LOG.error("IllegalArgument cronExpression!");
        }
    }

    /**
     * 关闭计划
     *
     * @param waitForJobsToComplete
     */
    public void shutdown(boolean waitForJobsToComplete) {
        String instanceKey = jobGroupName + "_" + triggerGroupName;
        if (instanceMap.containsKey(instanceKey)) {
            QuartzUtil quartzUtil = instanceMap.get(instanceKey);
            if (quartzUtil != null && quartzUtil.scheduler != null) {
                try {
                    quartzUtil.scheduler.shutdown(waitForJobsToComplete);
                } catch (SchedulerException ex) {
                    LOG.error(ex.getLocalizedMessage(), ex);
                }
            }
        }
    }

    public void shutdown() {
        shutdown(false);
    }

    /**
     * 检查此任务是否存在
     *
     * @param jobName
     * @param triggerName
     * @return
     */
    public Scheduler isSchedulerExist(String jobName, String triggerName) {
        String schedulerKey = jobGroupName + "<" + jobName + ">" + "-" + triggerGroupName + "<" + triggerName + ">";
        if (schedulerMap.containsKey(schedulerKey)) {
            return schedulerMap.get(schedulerKey);
        } else {
            return null;
        }
    }

    /**
     * 删除触发器和任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey tk = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //停止触发器
            scheduler.pauseTrigger(tk);
            //移除触发器
            scheduler.unscheduleJob(tk);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            //删除作业
            scheduler.deleteJob(jobKey);
            LOG.info("delete job => [JobName：" + jobName + " JobGroup：" + jobGroupName + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOG.error("delete job => [JobName：" + jobName + " JobGroup：" + jobGroupName + "] ");
        }
    }
}
