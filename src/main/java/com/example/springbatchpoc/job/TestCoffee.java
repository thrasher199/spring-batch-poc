package com.example.springbatchpoc.job;

import com.example.springbatchpoc.configuration.DbToDbBatchConfiguration;
import com.example.springbatchpoc.configuration.FileToDbBatchConfiguration;
import com.example.springbatchpoc.domain.Coffee;
import com.example.springbatchpoc.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;

@Configuration
@Lazy
@Slf4j
public class TestCoffee extends FileToDbBatchConfiguration {
    @Autowired
    public CoffeeRepository coffeeRepository;

   /* @Autowired
    public JobBuilderFactory jobBuilderFactory;*/

    @Bean
    @JobScope
    public Step coffeeStep(){
        return createBaseStep("coffeeStep").build();
    }

    /*@Bean
    public Job coffeeExtractJob(Step coffeeStep, Step employeeStep){
        return jobBuilderFactory.get("coffeeExtractJob")
                .incrementer(new RunIdIncrementer())
                .start(coffeeStep)
                .next(employeeStep)
                .build();
    }*/

    @Override
    public ItemProcessor<Coffee, Coffee> itemProcessor() {
        return (x) -> {
            log.info("Processing Id" + x.getId());
            return x;
        };
    }

    @Override
    public Class getTargetType() {
        return Coffee.class;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"id","brand", "origin", "characteristics", "stockCount"};
    }

    @Override
    public CrudRepository getWriterRepository() {
        return coffeeRepository;
    }

    @Override
    public String getWriterMethodName() {
        return "save";
    }
}
