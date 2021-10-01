package com.example.springbatchpoc.configuration.modular;

import com.example.springbatchpoc.job.CoffeeDbToDbJob;
import com.example.springbatchpoc.job.CoffeeEmployeeJob;
import com.example.springbatchpoc.job.MonthlyDsbQueryReport.MonthlyDisQureryReportJob;
import com.example.springbatchpoc.job.TestCoffee;
import com.example.springbatchpoc.job.TestEmployee;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing(modular = true)
public class ModularConfiguration {

   /* @Bean
    public ApplicationContextFactory employeeJob() {
        return new GenericApplicationContextFactory(TestEmployee.class);
    }

    @Bean
    public ApplicationContextFactory coffeeJob() {
        return new GenericApplicationContextFactory(TestCoffee.class);
    }*/

    @Bean
    public ApplicationContextFactory coffeeStockJob() {
        return new GenericApplicationContextFactory(CoffeeDbToDbJob.class);
    }

    @Bean
    public ApplicationContextFactory coffeeEmployeeJob() {
        return new GenericApplicationContextFactory(CoffeeEmployeeJob.class);
    }

    public ApplicationContextFactory monthlyDisQureryReportJob() {
        return new GenericApplicationContextFactory(MonthlyDisQureryReportJob.class);
    }

}
