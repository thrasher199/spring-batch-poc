package com.example.springbatchpoc.configuration;

import com.example.springbatchpoc.listener.ChunkLogListener;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class DbToDbBatchConfiguration {

    @Setter
    private JpaRepository readerRepository;
    @Setter
    private String readerRepoMethodName;
    @Setter
    private int pageSize;
    @Setter
    private JpaRepository writerRepository;
    @Setter
    private String writerRepoMethodName;
    @Setter
    private Map<String, Sort.Direction> sorts = Collections.singletonMap("id", Sort.Direction.ASC);

    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    public final void setStepBuilderFactory(StepBuilderFactory stepBuilderFactory){
        this.stepBuilderFactory = stepBuilderFactory;
    }

    public ChunkLogListener chunkLogListener(){
        return new ChunkLogListener();
    }

    @Bean
    public RepositoryItemReader repositoryItemReader(){
        return new RepositoryItemReaderBuilder<>()
                .name("repositoryItemReader")
                .repository(readerRepository)
                .methodName(readerRepoMethodName)
                .pageSize(pageSize)
                .sorts(sorts)
                .build();
    }

    public abstract ItemProcessor itemProcessor();

    @Bean(name = "dbToDbasyncItemProcessor")
    public AsyncItemProcessor asyncItemProcessor(){
        AsyncItemProcessor processor = new AsyncItemProcessor();
        processor.setDelegate(itemProcessor());
        return processor;
    }

    @Bean(name = "dbToDbrepositoryItemWriter")
    public RepositoryItemWriter repositoryItemWriter(){
        return new RepositoryItemWriterBuilder()
                .repository(writerRepository)
                .methodName(writerRepoMethodName)
                .build();
    }

    @Bean(name = "dbToDbasyncItemWriter")
    public AsyncItemWriter asyncItemWriter(){
        AsyncItemWriter asyncItemWriter = new AsyncItemWriter();
        asyncItemWriter.setDelegate(repositoryItemWriter());
        return asyncItemWriter;
    }

    protected SimpleStepBuilder createBaseStep(){
        return (SimpleStepBuilder) this.stepBuilderFactory.get("step1")
                .chunk(pageSize)
                .reader(repositoryItemReader())
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .faultTolerant()
                .listener(chunkLogListener())
                .allowStartIfComplete(true);
    }
}
