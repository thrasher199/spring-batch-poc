package com.example.springbatchpoc.quartz;

import com.example.springbatchpoc.domain.JobScheduler;
import com.example.springbatchpoc.domain.JobTemplate;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class QuartzClient {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    @Autowired
    private Scheduler scheduler;

    public void scheduleJob() throws SchedulerException {

        JobScheduler jobScheduler = getJobScheduler();

        jobScheduler.getJobTemplateSet().forEach(x -> {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jobName", x.getJobName());
            jobDataMap.put("inputFile", x.getInputFilePath());
            jobDataMap.put("chunkSize", x.getChunkSize());
            jobDataMap.put("jobLauncher", jobLauncher);
            jobDataMap.put("jobLocator", jobLocator);

            JobDetail jobDetail = JobBuilder.newJob(QuartzJobLauncher.class)
                    .withIdentity(x.getJobName(), jobScheduler.getSchedulerName())
                    .setJobData(jobDataMap)
                    .build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(x.getJobName().concat("_TRIGGER"), jobScheduler.getSchedulerName().concat("_TRIGGER"))
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(jobScheduler.getCronTab()))
                    .withPriority(x.getJobPriority())
                    .build();

            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.addJob();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });

    }

    private JobScheduler getJobScheduler(){
        JobTemplate job1 = JobTemplate.builder()
                .jobName("coffeeExtractJob")
                .inputFilePath("E:/playground/spring-batch-input-file/coffee-list.csv")
                .chunkSize(2)
                .jobPriority(1)
                .build();

        JobTemplate job2 = JobTemplate.builder()
                .jobName("employeeExtractJob")
                .inputFilePath("E:/playground/spring-batch-input-file/employer-100.csv")
                .chunkSize(5)
                .jobPriority(99)
                .build();

        Set<JobTemplate> jobTemplateSet = new HashSet<>();
        jobTemplateSet.add(job1);
        jobTemplateSet.add(job2);


        JobScheduler jobScheduler = JobScheduler.builder()
                .schedulerName("try")
                .jobTemplateSet(jobTemplateSet)
                .cronTab("0 45 15 1/1 * ? *").build();

        return jobScheduler;
    }
}
