package com.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import java.util.List;

/**
 * Created by wuhuachuan on 16/6/9.
 *
 * 该类主要用于单机版本
 */

@Slf4j
//@Configuration
public class SchedulerJob {

    private final static String CRON_EXPRESSION = "*/3 * * * * ?";

    @Bean
    public JobDetailFactoryBean createJobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();jobDetailFactoryBean.setName("myJobName");
        jobDetailFactoryBean.setGroup("myJobGroup");

        jobDetailFactoryBean.setJobClass(MyJob.class);

        return jobDetailFactoryBean;
    }

    @Bean
    protected CronTriggerFactoryBean createTrigger(JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("myTriggerName");
        cronTriggerFactoryBean.setGroup("myTriggerGroup");

        cronTriggerFactoryBean.setCronExpression(CRON_EXPRESSION);
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);

        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(List<Trigger> triggers){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setSchedulerName("myScheduleName");

        schedulerFactoryBean.setTriggers(triggers.toArray(new Trigger[0]));

        return schedulerFactoryBean;
    }
}
