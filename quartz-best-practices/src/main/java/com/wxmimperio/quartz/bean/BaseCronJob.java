package com.wxmimperio.quartz.bean;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BaseCronJob implements Job {

    private String jobName;
    private String jobGroupName;
    private String triggerGroupName;
    private String cronTab;
    private JobDataMap jobDataMap;


    public BaseCronJob() {
    }

    public BaseCronJob(String jobName, String jobGroupName, String triggerGroupName, String cronTab, JobDataMap jobDataMap) {
        this.jobName = jobName;
        this.jobGroupName = jobGroupName;
        this.triggerGroupName = triggerGroupName;
        this.cronTab = cronTab;
        this.jobDataMap = jobDataMap;
    }

    public void validation() throws JobExecutionException {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName) || StringUtils.isEmpty(triggerGroupName) || StringUtils.isEmpty(cronTab)) {
            throw new JobExecutionException("jobName|jobGroupName|triggerGroupName|cronTab can not be empty.");
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCronTab() {
        return cronTab;
    }

    public void setCronTab(String cronTab) {
        this.cronTab = cronTab;
    }

    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    @Override
    public String toString() {
        return "BaseCronJob{" +
                "jobName='" + jobName + '\'' +
                ", jobGroupName='" + jobGroupName + '\'' +
                ", triggerGroupName='" + triggerGroupName + '\'' +
                ", cronTab='" + cronTab + '\'' +
                ", jobDataMap=" + jobDataMap +
                '}';
    }
}
