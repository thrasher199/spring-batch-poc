package com.example.springbatchpoc.job;

import com.example.springbatchpoc.configuration.FileToDbBatchConfiguration;
import com.example.springbatchpoc.domain.Employee;
import com.example.springbatchpoc.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class TestEmployee extends FileToDbBatchConfiguration {

    @Value("${file.input.employee}")
    private String fileInput;

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Override
    public ItemProcessor itemProcessor() {
        return (employee) -> {
            Thread.sleep(5);
            return employee;
        };
    }

    @Bean
    public Step step1(){
        setChunkSize(1);
        setInputFile(fileInput);
        setType(Employee.class);
        setRepository(employeeRepository);
        setMethodName("save");
        setNames(new String[]{"empId",
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
                "password",});

        return createBaseStep().build();

    }

    @Bean
    public Job employeeExtractJob(){
        return jobBuilderFactory.get("employeeExtractJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }
}
