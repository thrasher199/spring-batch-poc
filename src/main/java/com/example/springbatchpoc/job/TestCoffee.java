package com.example.springbatchpoc.job;

import com.example.springbatchpoc.configuration.FileToDbBatchConfiguration;
import com.example.springbatchpoc.domain.Coffee;
import com.example.springbatchpoc.domain.Employee;
import com.example.springbatchpoc.repository.CoffeeRepository;
import com.example.springbatchpoc.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Slf4j
public class TestCoffee extends FileToDbBatchConfiguration {
    @Value("${file.input}")
    private String fileInput;

    @Autowired
    public CoffeeRepository coffeeRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;



    @Bean
    public Step step1(){
        setChunkSize(5);
        setInputFile(fileInput);
        setType(Coffee.class);
        setRepository(coffeeRepository);
        setMethodName("save");
        setNames(new String[]{"id","brand", "origin", "characteristics", "stockCount"});

        return createBaseStep().build();

    }

    @Bean
    public Job coffeeExtractJob(){
        return jobBuilderFactory.get("coffeeExtractJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }

    @Override
    public ItemProcessor<Coffee, Coffee> itemProcessor() {
        return (x) -> {
            log.info("Processing Id" + x.getId());
            return x;
        };
    }
}
