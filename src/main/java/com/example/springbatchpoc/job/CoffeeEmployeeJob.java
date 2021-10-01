package com.example.springbatchpoc.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;


@Configuration
@Lazy
@Import({TestCoffee.class, TestEmployee.class})
public class CoffeeEmployeeJob {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job coffeeExtractJob(Step coffeeStep, Step employeeStep){
        return jobBuilderFactory.get("coffeeExtractJob")
                .incrementer(new RunIdIncrementer())
                .start(coffeeStep)
                .next(employeeStep)
                .build();
    }
}
