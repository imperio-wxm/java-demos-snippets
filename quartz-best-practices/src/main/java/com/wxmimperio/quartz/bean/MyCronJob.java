package com.wxmimperio.quartz.bean;

import org.quartz.*;

import java.util.Date;

public class MyCronJob extends BaseCronJob {

    public MyCronJob() {
    }

    public MyCronJob(String jobName, String jobGroupName, String triggerGroupName, String cronTab, JobDataMap jobDataMap) {
        super(jobName, jobGroupName, triggerGroupName, cronTab, jobDataMap);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 调度时间
        Date date = jobExecutionContext.getFireTime();
        // 附加参数
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        // Job 详情
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        System.out.println(date);
    }
}
