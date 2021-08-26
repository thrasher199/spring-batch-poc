package com.example.springbatchpoc.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.util.StopWatch;

public class ExecutionTimeJobListener implements JobExecutionListener {

    private Logger logger = LoggerFactory.getLogger(ExecutionTimeJobListener.class);
    private StopWatch stopWatch = new StopWatch();

    @Override
    public void beforeJob(JobExecution jobExecution) {
        stopWatch.start();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        stopWatch.stop();
        logger.info("Job took " + stopWatch.getTotalTimeSeconds() + "s");
    }
}
