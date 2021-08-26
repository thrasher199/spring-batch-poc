package com.example.springbatchpoc.configuration;

import com.example.springbatchpoc.domain.Coffee;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;

public abstract class AbstractJobConfiguration<T> {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    public SimpleStepBuilder<T, T> createBaseStep(String stepName, CrudRepository repository, int chuck, String fileInput) {
        return stepBuilderFactory.get(stepName)
                .<T, T>chunk(5)
                .reader(flatFileItemReader(fileInput))
                .writer(repositoryItemWriter(repository));
    }

    @Bean
    public FlatFileItemReader flatFileItemReader(String fileInput) {
        return new FlatFileItemReaderBuilder()
                .name("coffeeItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[]{"id","brand", "origin", "characteristics"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
                    setTargetType(Coffee.class);
                }})
                .build();
    }

    @Bean
    public RepositoryItemWriter repositoryItemWriter(CrudRepository repository){
        return new RepositoryItemWriterBuilder<Coffee>().repository(repository).methodName("save").build();
    }
}
