package com.example.springbatchpoc.configuration;

import com.example.springbatchpoc.listener.CommonItemListener;
import com.example.springbatchpoc.listener.ItemProcessorFailureLoggerListener;
import com.example.springbatchpoc.listener.ItemReaderFailureLoggerListener;
import com.example.springbatchpoc.listener.ItemWriterFailureLoggerListener;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.CrudRepository;

public abstract class FileToDbBatchConfiguration<T> {

    @Setter
    @NonNull
    private String inputFile;
    @Setter
    @NonNull
    private Class type;
    @Setter
    @NonNull
    private String[] names;
    @Setter
    @NonNull
    private CrudRepository repository;
    @Setter
    @NonNull
    private String methodName;
    @Setter
    private int chunkSize;


    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReaderFailureLoggerListener itemReaderFailureLoggerListener(){
        return new ItemReaderFailureLoggerListener();
    }

    @Bean
    public ItemProcessorFailureLoggerListener itemProcessorFailureLoggerListener(){
        return new ItemProcessorFailureLoggerListener();
    }

    @Bean
    public ItemWriterFailureLoggerListener itemWriteFailureLoggerListener(){
        return new ItemWriterFailureLoggerListener();
    }

    @Bean
    public FlatFileItemReader flatFileItemReader(String fileInput, Class type, String[] names){
        return new FlatFileItemReaderBuilder<>()
                .name("flatFileItemReader")
                .resource(new FileSystemResource(fileInput))
                .delimited()
                .names(names)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(type);
                }})
                .build();
    }

    @Bean
    public RepositoryItemWriter repositoryItemWriter(CrudRepository repository, String methodName){
        return new RepositoryItemWriterBuilder()
                .repository(repository)
                .methodName(methodName)
                .build();
    }

    @Bean
    public AsyncItemWriter asyncItemWriter(CrudRepository repository, String methodName){
        AsyncItemWriter writer = new AsyncItemWriter();
        writer.setDelegate(repositoryItemWriter(repository, methodName));
        return writer;
    }

    public abstract ItemProcessor<T, T> itemProcessor();

    @Bean
    public AsyncItemProcessor asyncItemProcessor(){
        AsyncItemProcessor processor = new AsyncItemProcessor();
        processor.setDelegate(itemProcessor());
        return processor;
    }

    public SimpleStepBuilder createBaseStep(){
        return (SimpleStepBuilder) stepBuilderFactory.get("step1")
                .chunk(chunkSize)
                .reader(flatFileItemReader(inputFile, type, names))
                .listener((ItemProcessListener<? super Object, ? super Object>) itemReaderFailureLoggerListener())
                .processor(asyncItemProcessor())
                .listener((ItemProcessListener) itemProcessorFailureLoggerListener())
                .writer(asyncItemWriter(repository, methodName))
                .listener((ItemProcessListener) itemWriteFailureLoggerListener())
                .faultTolerant()
                .allowStartIfComplete(true);
    }

    public Step createBaseStep(String stepName,
                               int chunkSize,
                               String fileInput,
                               Class type,
                               String[] names,
                               CrudRepository repository,
                               String method){
        return (Step) stepBuilderFactory.get("step1")
                .chunk(chunkSize)
                .reader(flatFileItemReader(fileInput, type, names))
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter(repository, method))
                .faultTolerant();
    }

}
