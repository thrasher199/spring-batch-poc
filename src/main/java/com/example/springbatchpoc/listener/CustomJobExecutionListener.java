package com.example.springbatchpoc.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

public class CustomJobExecutionListener extends JobExecutionListenerSupport {

    private ExecutionContext executionContext;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        this.executionContext = jobExecution.getExecutionContext();
    }
}
