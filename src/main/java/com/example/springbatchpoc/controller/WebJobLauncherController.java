package com.example.springbatchpoc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class WebJobLauncherController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    JobRegistry jobRegistry;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobOperator jobOperator;

    @RequestMapping("/launchjob")
    public String handle() throws Exception {

        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "jobExecution's info: Id = " + jobExecution.getId() + " ,status = " + jobExecution.getExitStatus();
    }

    @RequestMapping("/restartjob")
    public void restartUncompletedJobs() {
        try {
            jobRegistry.register(new ReferenceJobFactory(job));

            List<String> jobs = jobExplorer.getJobNames();
            for (String job : jobs) {
                JobInstance jobInstance = jobExplorer.getLastJobInstance(job);
                jobOperator.restart(jobInstance.getInstanceId());
                log.info("Restarted: " + jobInstance.getInstanceId());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
