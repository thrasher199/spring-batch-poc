package com.example.springbatchpoc.job;

import com.example.springbatchpoc.domain.Employee;
import com.example.springbatchpoc.listener.ExecutionTimeJobListener;
import com.example.springbatchpoc.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

//@Configuration
public class EmployeeBatchJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    public EmployeeRepository employeeRepository;

    @Value("${file.input.employee}")
    private String dataFile;

    @Bean
    @StepScope
    public FlatFileItemReader<Employee> employeeItemReader(){
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .delimited()
                .names(new String[]{"empId",
                        "namePrefix",
                        "firstName",
                        "middleInitial",
                        "lastName",
                        "gender",
                        "email",
                        "fatherName",
                        "motherName",
                        "motherMaidenName",
                        "dob",
                        "tob",
                        "age",
                        "weight",
                        "dateOfJoining",
                        "quarterOfJoining",
                        "halfOfJoining",
                        "yearOfJoining",
                        "monthOfJoining",
                        "monthNameOfJoining",
                        "shortMonth",
                        "dayOfJoining",
                        "dowOfJoining",
                        "shortDOW",
                        "ageInCompany",
                        "salary",
                        "lastPercentageHike",
                        "ssn",
                        "phoneNo",
                        "placeName",
                        "county",
                        "city",
                        "state",
                        "zip",
                        "region",
                        "userName",
                        "password",})
                .targetType(Employee.class)
                .resource(new ClassPathResource(dataFile))
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter employeeItemWriter(){
        return new RepositoryItemWriterBuilder<Employee>()
                .repository(employeeRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public AsyncItemWriter<Employee> asyncItemWriter(){
        AsyncItemWriter<Employee> writer = new AsyncItemWriter<>();
        writer.setDelegate(employeeItemWriter());
        return writer;
    }


    @Bean
    public ItemProcessor<Employee, Employee> employeeItemProcessor(){
        return (employee) -> {
            Thread.sleep(5);
            return employee;
        };
    }

    @Bean
    public AsyncItemProcessor<Employee, Employee> asyncItemProcessor(){
        AsyncItemProcessor<Employee, Employee> processor = new AsyncItemProcessor<>();
        processor.setDelegate(employeeItemProcessor());
        return processor;
    }

    @Bean
    public Step step1Async(){
        return this.stepBuilderFactory.get("step1Async")
                   .chunk(100)
                   .reader(employeeItemReader())
                   .processor((ItemProcessor) asyncItemProcessor())
                   .writer(asyncItemWriter())
                   .build();
    }

    @Bean
    public Job employeeAsyncJob(){
        return this.jobBuilderFactory.get("employeeAsyncJob")
                .incrementer(new RunIdIncrementer())
                .listener(new ExecutionTimeJobListener())
                .flow(step1Async())
                .end().build();

    }
}
