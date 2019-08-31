package com.wxmimperio.quartz;


import com.wxmimperio.quartz.bean.MyCronJob;
import com.wxmimperio.quartz.utils.QuartzUtils;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QuartzMain implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(QuartzMain.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws SchedulerException {
        String jobName = "wxm";
        String jobGroupName = "wxm_group";
        String triggerGroupName = "wxm_trigger";
        String cronTab = "*/2 * * * * ?";
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("test", 1);
        MyCronJob myCronJob = new MyCronJob(jobName, jobGroupName, triggerGroupName, cronTab, jobDataMap);
        QuartzUtils.addAndUpdateJobByCronTrigger(MyCronJob.class, myCronJob);
        QuartzUtils.startScheduler();
    }
}
