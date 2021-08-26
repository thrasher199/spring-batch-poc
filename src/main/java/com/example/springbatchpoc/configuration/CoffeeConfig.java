package com.example.springbatchpoc.configuration;

import com.example.springbatchpoc.repository.CoffeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CoffeeConfig extends AbstractJobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public CoffeeRepository coffeeRepository;

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public Job coffeeJob(Step step1){
        return jobBuilderFactory.get("coffeeJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(){

        return createBaseStep("step1", coffeeRepository, 5, fileInput).build();
    }


}
