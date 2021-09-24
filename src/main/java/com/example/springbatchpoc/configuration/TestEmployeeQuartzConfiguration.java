package com.example.springbatchpoc.configuration;

import com.example.springbatchpoc.quartz.QuartzJobLauncher;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class TestEmployeeQuartzConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry){
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor =  new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);


        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);

        Map<String, Object> map = new HashMap<>();
        map.put("jobName", "employeeExtractJob");
        map.put("inputFile", "E:/playground/spring-batch-input-file/employer-100.csv");
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator", jobLocator);

        jobDetailFactoryBean.setJobDataAsMap(map);

        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        //run every 10 seconds
        cronTriggerFactoryBean.setCronExpression("0 0/3 * 1/1 * ? *");

        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        schedulerFactoryBean.setAutoStartup(true);

        return schedulerFactoryBean;
    }
}
