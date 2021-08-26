package com.example.springbatchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableBatchProcessing
//@EnableJpaRepositories(basePackages  = "com.example.springbatchpoc.repository")
@EnableJpaRepositories
public class SpringBatchPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPocApplication.class, args);
	}

}
