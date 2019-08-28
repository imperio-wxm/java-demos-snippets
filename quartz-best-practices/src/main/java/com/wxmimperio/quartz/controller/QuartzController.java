package com.wxmimperio.quartz.controller;

import com.wxmimperio.quartz.bean.MyCronJob;
import com.wxmimperio.quartz.utils.QuartzUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("quartz")
public class QuartzController {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzController.class);

    @PutMapping("jobs/pauseJob")
    public void pauseJob(@RequestParam String jobName, @RequestParam String jobGroupName) {
        try {
            if (QuartzUtils.hasJobKey(jobName, jobGroupName)) {
                QuartzUtils.pauseJob(jobName, jobGroupName);
                LOG.info(String.format("Pause job = %s in group = %s", jobName, jobGroupName));
            } else {
                LOG.error(String.format("Can not get job = %s in group = %s", jobName, jobGroupName));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("jobs/resumeJob")
    public void resumeJob(@RequestParam String jobName, @RequestParam String jobGroupName) {
        try {
            if (QuartzUtils.hasJobKey(jobName, jobGroupName)) {
                QuartzUtils.resumeJob(jobName, jobGroupName);
                LOG.info(String.format("Resume job = %s in group = %s", jobName, jobGroupName));
            } else {
                LOG.error(String.format("Can not get job = %s in group = %s", jobName, jobGroupName));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("jobs/pauseTrigger")
    public void pauseTrigger(@RequestParam String jobName, @RequestParam String triggerGroupName) {
        try {
            if (QuartzUtils.hasTrigger(jobName, triggerGroupName)) {
                QuartzUtils.pauseTrigger(jobName, triggerGroupName);
                LOG.info(String.format("PauseTrigger job = %s in trigger group = %s", jobName, triggerGroupName));
            } else {
                LOG.error(String.format("Can not get job = %s in trigger group = %s", jobName, triggerGroupName));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("jobs/resumeTrigger")
    public void resumeTrigger(@RequestParam String jobName, @RequestParam String triggerGroupName) {
        try {
            if (QuartzUtils.hasTrigger(jobName, triggerGroupName)) {
                QuartzUtils.resumeTrigger(jobName, triggerGroupName);
                LOG.info(String.format("ResumeTrigger job = %s in trigger group = %s", jobName, triggerGroupName));
            } else {
                LOG.error(String.format("Can not get job = %s in trigger group = %s", jobName, triggerGroupName));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("jobs/pauseJobNoMakeUp")
    public MyCronJob pauseJobNoMakeUp(@RequestParam String jobName, @RequestParam String triggerGroupName) {
        try {
            if (QuartzUtils.hasTrigger(jobName, triggerGroupName)) {
                JobDetail jobDetail = QuartzUtils.pauseJobNoMakeUp(jobName, triggerGroupName);
                LOG.info(String.format("PauseJobNoMakeUp job = %s in trigger group = %s", jobName, triggerGroupName));
                return new MyCronJob(
                        jobName,
                        jobDetail.getKey().getGroup(),
                        triggerGroupName,
                        null,
                        jobDetail.getJobDataMap()
                );
            } else {
                LOG.error(String.format("Can not get job = %s in trigger group = %s", jobName, triggerGroupName));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("jobs/updateJob")
    public MyCronJob updateJob(@RequestParam String jobName, @RequestParam String jobGroupName,
                               @RequestParam String triggerGroupName, @RequestParam String cronTab, @RequestParam(required = false) Map<String, Object> map) {
        MyCronJob myCronJob = new MyCronJob(jobName, jobGroupName, triggerGroupName, cronTab, new JobDataMap(map));
        try {
            QuartzUtils.addAndUpdateJobByCronTrigger(MyCronJob.class, myCronJob);
            LOG.info(String.format("Job = %s in trigger group = %s updated", jobName, triggerGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return myCronJob;
    }
}
