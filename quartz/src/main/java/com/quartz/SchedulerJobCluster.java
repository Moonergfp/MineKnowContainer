package com.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by wuhuachuan on 16/6/11.
 *
 * 该类主要用于集群版本
 */

@Slf4j
@Configuration
public class SchedulerJobCluster {

    private final static String CRON_EXPRESSION = "*/5 * * * * ?";

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
    public SchedulerFactoryBean schedulerFactoryBean(List<Trigger> triggers, DataSource dataSource){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        schedulerFactoryBean.setTriggers(triggers.toArray(new Trigger[0]));
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setDataSource(dataSource);

        return schedulerFactoryBean;
    }

    private Properties quartzProperties(){
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        try {
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException e) {
            log.error("read quartz.properties file error: {}", e.getMessage());
        }
        return null;
    }
}
