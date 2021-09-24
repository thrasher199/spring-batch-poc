package com.example.springbatchpoc.controller;

import com.example.springbatchpoc.domain.JobTemplate;
import com.example.springbatchpoc.job.TestCoffee;
import com.example.springbatchpoc.quartz.QuartzClient;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Slf4j
public class WebJobLauncherController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobRegistry jobRegistry;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    QuartzClient quartzClient;

    @RequestMapping("/launchjob")
    public String handle() throws Exception {

        JobExecution jobExecution = null;
        try {
            //jobExecution = jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "jobExecution's info: Id = " + jobExecution.getId() + " ,status = " + jobExecution.getExitStatus();
    }

    @RequestMapping("/master-run")
    public void masterRun() throws Exception{

        JobTemplate job1 = JobTemplate.builder()
                .jobName("coffeeExtractJob")
                .inputFilePath("E:/playground/spring-batch-input-file/coffee-list.csv")
                .chunkSize(2)
                .build();

        JobTemplate job2 = JobTemplate.builder()
                .jobName("employeeExtractJob")
                .inputFilePath("E:/playground/spring-batch-input-file/employer-100.csv")
                .chunkSize(5)
                .build();




        List<JobTemplate> jobTemplateList = new ArrayList<>();
        jobTemplateList.add(job1);
        jobTemplateList.add(job2);

        AtomicReference<String> status;

        jobTemplateList.forEach(x -> {
            // Pass the required Job Parameters from here to read it anywhere within
            // Spring Batch infrastructure
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobId", x.getJobName() + System.currentTimeMillis())
                    .addString("inputFile", x.getInputFilePath())
                    .addLong("chunkSize", x.getChunkSize()).toJobParameters();

            JobExecution execution = null;
            try {
                Job job = jobRegistry.getJob(x.getJobName());
                execution = jobLauncher.run(job, jobParameters);
            } catch (JobExecutionAlreadyRunningException e) {
                e.printStackTrace();
            } catch (JobRestartException e) {
                e.printStackTrace();
            } catch (JobInstanceAlreadyCompleteException e) {
                e.printStackTrace();
            } catch (JobParametersInvalidException e) {
                e.printStackTrace();
            } catch (NoSuchJobException e) {
                e.printStackTrace();
            }
            System.out.println("STATUS :: " + execution.getStatus());
        });

    }

    @RequestMapping("/restartjob")
    public void restartUncompletedJobs() {
       /* try {
            jobRegistry.register(new ReferenceJobFactory(job));

            List<String> jobs = jobExplorer.getJobNames();
            for (String job : jobs) {
                JobInstance jobInstance = jobExplorer.getLastJobInstance(job);
                jobOperator.restart(jobInstance.getInstanceId());
                log.info("Restarted: " + jobInstance.getInstanceId());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }*/
    }

    @RequestMapping("/scheduleJob")
    public void scheduleJob() throws SchedulerException {
        quartzClient.scheduleJob();
    }
}
