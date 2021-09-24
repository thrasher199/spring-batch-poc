package com.example.springbatchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class BatchTestConfig {

    @Bean
    public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilderFactory(jobRepository, transactionManager);
    }

}
