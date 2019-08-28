package com.wxmimperio.quartz.utils;

import com.wxmimperio.quartz.bean.BaseCronJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzUtils {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzUtils.class);

    /**
     * 调度器工厂类
     */
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * 获取调度器
     *
     * @return
     * @throws SchedulerException
     */
    public static Scheduler getScheduler() throws SchedulerException {
        return schedulerFactory.getScheduler();
    }

    public static TriggerKey getTriggerKey(String jobName, String triggerGroupName) {
        return TriggerKey.triggerKey(jobName, triggerGroupName);
    }

    /**
     * 获取 CronTrigger
     *
     * @param jobName          任务名
     * @param triggerGroupName 触发组名
     * @param cronTab
     * @return
     * @throws SchedulerException
     */
    public static CronTrigger getCronTrigger(String jobName, String triggerGroupName, String cronTab) throws SchedulerException {
        TriggerKey triggerKey = getTriggerKey(jobName, triggerGroupName);
        CronTrigger trigger = (CronTrigger) getScheduler().getTrigger(triggerKey);
        // 不存在则新生成一个
        if (null == trigger) {
            return TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cronTab)).build();
        } else {
            return trigger;
        }
    }

    /**
     * 设置Job详情和cron触发到调度
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerGroupName 触发组名
     * @param cronTab          触发时间
     * @param cls              任务实现类
     * @param jobDataMap       任务附件参数
     * @throws SchedulerException
     */
    public static void setJobDetailAndCronTriggerInScheduler(String jobName, String jobGroupName, String triggerGroupName, String cronTab, Class<? extends Job> cls, JobDataMap jobDataMap) throws SchedulerException {
        JobDetail jobDetail = getJobDetail(jobName, jobGroupName, cls, jobDataMap);
        CronTrigger trigger = getCronTrigger(jobName, triggerGroupName, cronTab);
        if (hasJobKey(jobName, jobGroupName)) {
            TriggerKey triggerKey = getTriggerKey(jobName, triggerGroupName);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cronTab).withMisfireHandlingInstructionDoNothing()).build();
            getScheduler().rescheduleJob(triggerKey, trigger);
            LOG.info(String.format("Job = %s already exists in group = %s and is being updated.....", jobName, jobGroupName));
        } else {
            getScheduler().scheduleJob(jobDetail, trigger);
            LOG.info(String.format("Job = %s add in group = %s", jobName, jobGroupName));
        }
    }

    /**
     * 获取Job 详情
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @param cls          任务实现类
     * @param jobDataMap   任务附加参数
     * @return
     */
    public static JobDetail getJobDetail(String jobName, String jobGroupName, Class<? extends Job> cls, JobDataMap jobDataMap) {
        return jobDataMap == null || jobDataMap.isEmpty() ? JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build() :
                JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).usingJobData(jobDataMap).build();
    }

    /**
     * 判断JobKey是否存在
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @return
     * @throws SchedulerException
     */
    public static boolean hasJobKey(String jobName, String jobGroupName) throws SchedulerException {
        return getScheduler().checkExists(JobKey.jobKey(jobName, jobGroupName));
    }

    public static boolean hasTrigger(String jobName, String triggerGroupName) throws SchedulerException {
        return null != getScheduler().getTrigger(getTriggerKey(jobName, triggerGroupName));
    }

    public static void addJobByCronTrigger(Class<? extends Job> cls, BaseCronJob baseCronJob) throws SchedulerException {
        baseCronJob.validation();
        setJobDetailAndCronTriggerInScheduler(
                baseCronJob.getJobName(),
                baseCronJob.getJobGroupName(),
                baseCronJob.getTriggerGroupName(),
                baseCronJob.getCronTab(),
                cls,
                baseCronJob.getJobDataMap()
        );
    }

    public static void startScheduler() throws SchedulerException {
        getScheduler().start();
    }

    public static void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        getScheduler().pauseJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 恢复job 带有补偿机制
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    public static void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        getScheduler().resumeJob(JobKey.jobKey(jobName, jobGroupName));
    }

    public static void unscheduleJob(String jobName, String triggerGroupName) throws SchedulerException {
        TriggerKey triggerKey = getTriggerKey(jobName, triggerGroupName);
        getScheduler().unscheduleJob(triggerKey);
    }

    public static void pauseTrigger(String jobName, String triggerGroupName) throws SchedulerException {
        TriggerKey triggerKey = getTriggerKey(jobName, triggerGroupName);
        getScheduler().pauseTrigger(triggerKey);
    }

    /**
     * 恢复trigger 带有补偿机制
     *
     * @param jobName
     * @param triggerGroupName
     * @throws SchedulerException
     */
    public static void resumeTrigger(String jobName, String triggerGroupName) throws SchedulerException {
        TriggerKey triggerKey = getTriggerKey(jobName, triggerGroupName);
        getScheduler().resumeTrigger(triggerKey);
    }


}
