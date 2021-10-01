package com.example.springbatchpoc.job.MonthlyDsbQueryReport;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(MOnthDisQueyStepName.class)
public class MonthlyDisQureryReportJob {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job monthlyDisQureryReportJob(Step coffeeStep, Step employeeStep){
        return jobBuilderFactory.get("MonthlyDisQureryReportJob")
                .incrementer(new RunIdIncrementer())
                .start(coffeeStep)
                .next(employeeStep)
                .build();
    }
}
