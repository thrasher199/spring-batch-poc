package com.example.springbatchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableBatchProcessing(modular = true)
@EnableScheduling
public class SpringBatchPocApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPocApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBatchPocApplication.class);
	}

	/*@Bean
	public ApplicationContextFactory coffeeJob() {

		return new GenericApplicationContextFactory(TestCoffee.class);
	}

	@Bean
	public ApplicationContextFactory employeeJob() {

		return new GenericApplicationContextFactory(TestEmployee.class);
	}

	@Bean
	public ApplicationContextFactory coffeeStockJob() {
		return new GenericApplicationContextFactory(CoffeeDbToDbJob.class);
	}*/
}
