package com.example.springbatchpoc.job;

import com.example.springbatchpoc.configuration.FileToDbBatchConfiguration;
import com.example.springbatchpoc.domain.Employee;
import com.example.springbatchpoc.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;

@Configuration
@Lazy
@Slf4j
public class TestEmployee extends FileToDbBatchConfiguration {
    @Autowired
    public EmployeeRepository employeeRepository;

    @Override
    public ItemProcessor itemProcessor() {
        return (employee) -> {
            Thread.sleep(5);
            log.info("Thread sleep");
            return employee;
        };
    }

    @Override
    public Class getTargetType() {
        return Employee.class;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"empId",
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
                "password"};
    }

    @Override
    public CrudRepository getWriterRepository() {
        return employeeRepository;
    }

    @Override
    public String getWriterMethodName() {
        return "save";
    }

    @Bean
    @JobScope
    public Step employeeStep(){
        return createBaseStep("employeeStep").build();
    }

    /*@Bean
    public Job employeeExtractJob(Step employeeStep){
        return jobBuilderFactory.get("employeeExtractJob")
                .incrementer(new RunIdIncrementer())
                .flow(employeeStep)
                .end().build();
    }*/
}
