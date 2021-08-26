package com.example.springbatchpoc.listener;

import com.example.springbatchpoc.domain.Coffee;
import com.example.springbatchpoc.repository.CoffeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private ExecutionContext executionContext;

    @Override
    public void afterJob(JobExecution jobExecution) {
        this.executionContext = jobExecution.getExecutionContext();
    }
}
