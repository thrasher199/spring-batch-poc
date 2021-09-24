package com.example.springbatchpoc.configuration;

import com.example.springbatchpoc.listener.ChunkLogListener;
import com.example.springbatchpoc.listener.ItemProcessorFailureLoggerListener;
import com.example.springbatchpoc.listener.ItemReaderFailureLoggerListener;
import com.example.springbatchpoc.listener.ItemWriterFailureLoggerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.CrudRepository;

@Slf4j
public abstract class FileToDbBatchConfiguration<T> {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    public ItemReaderFailureLoggerListener itemReaderFailureLoggerListener(){
        return new ItemReaderFailureLoggerListener();
    }

    public ItemProcessorFailureLoggerListener itemProcessorFailureLoggerListener(){
        return new ItemProcessorFailureLoggerListener();
    }

    public ItemWriterFailureLoggerListener itemWriteFailureLoggerListener(){
        return new ItemWriterFailureLoggerListener();
    }

    public ChunkLogListener chunkLogListener(){
        return new ChunkLogListener();
    }

    @Bean
    public FlatFileItemReader flatFileItemReader(){
        return new FlatFileItemReaderBuilder<>()
                .name("flatFileItemReader")
                .resource(resource(null))
                .delimited()
                .names(getColumnNames())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(getTargetType());
                }})
                .build();
    }

    @Bean
    @StepScope
    public Resource resource(@Value("#{jobParameters['inputFile']}") String inputFile){
        return new FileSystemResource(inputFile);
    }

    @Bean
    @StepScope
    public CompletionPolicy completionPolicy(@Value("#{jobParameters['chunkSize']}") long chunkSize){
        return new SimpleCompletionPolicy(Math.toIntExact(chunkSize));
    }

    @Bean
    public RepositoryItemWriter repositoryItemWriter(){
        return new RepositoryItemWriterBuilder()
                .repository(getWriterRepository())
                .methodName(getWriterMethodName())
                .build();
    }

    @Bean
    public AsyncItemWriter asyncItemWriter(){
        AsyncItemWriter writer = new AsyncItemWriter();
        writer.setDelegate(repositoryItemWriter());
        return writer;
    }

    @Bean
    public abstract ItemProcessor<T, T> itemProcessor();

    public abstract Class getTargetType();
    public abstract String[] getColumnNames();
    public abstract CrudRepository getWriterRepository();
    public abstract String getWriterMethodName();

    @Bean
    public AsyncItemProcessor asyncItemProcessor(){
        AsyncItemProcessor processor = new AsyncItemProcessor();
        processor.setDelegate(itemProcessor());
        return processor;
    }

    public SimpleStepBuilder createBaseStep(){
        return (SimpleStepBuilder) stepBuilderFactory.get("step1")
                .chunk(completionPolicy(0))
                .reader(flatFileItemReader())
                .listener((ItemProcessListener<? super Object, ? super Object>) itemReaderFailureLoggerListener())
                .processor(asyncItemProcessor())
                .listener((ItemProcessListener) itemProcessorFailureLoggerListener())
                .writer(asyncItemWriter())
                .listener((ItemProcessListener) itemWriteFailureLoggerListener())
                .faultTolerant()
                .listener(chunkLogListener())
                .allowStartIfComplete(true);
    }
}
