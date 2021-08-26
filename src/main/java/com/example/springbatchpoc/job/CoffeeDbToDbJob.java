package com.example.springbatchpoc.job;

import com.example.springbatchpoc.configuration.DbToDbBatchConfiguration;
import com.example.springbatchpoc.domain.Coffee;
import com.example.springbatchpoc.domain.CoffeeStock;
import com.example.springbatchpoc.listener.CustomJobExecutionListener;
import com.example.springbatchpoc.processor.CoffeeItemProcessor;
import com.example.springbatchpoc.repository.CoffeeRepository;
import com.example.springbatchpoc.repository.CoffeeStockRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Configuration
public class CoffeeDbToDbJob extends DbToDbBatchConfiguration {


    @Autowired
    public CoffeeRepository coffeeRepository;

    @Autowired
    public CoffeeStockRepository coffeeStockRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;


    @Override
    public ItemProcessor<Coffee, CoffeeStock> itemProcessor() {
        return new CoffeeItemProcessor();
    }

    @Bean
    public Step step1(){
        setPageSize(5);
        setReaderRepository(coffeeRepository);
        setReaderRepoMethodName("findAll");
        setWriterRepository(coffeeStockRepository);
        setWriterRepoMethodName("save");
        setSorts(Collections.singletonMap("id", Sort.Direction.ASC));

        return createBaseStep().build();
    }

    @Bean
    public Job coffeStockJob(){
        return jobBuilderFactory.get("coffeStockJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }

}
