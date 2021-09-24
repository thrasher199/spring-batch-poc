package com.example.springbatchpoc.job;


import com.example.springbatchpoc.BatchTestConfig;
import com.example.springbatchpoc.domain.Coffee;
import com.example.springbatchpoc.domain.CoffeeStock;
import com.example.springbatchpoc.processor.CoffeeItemProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, CoffeeDbToDbJob.class})
public class CoffeeDbToDbJobTest {

    @Autowired
    private JobLauncherTestUtils testUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @InjectMocks
    private CoffeeItemProcessor coffeeItemProcessor = new CoffeeItemProcessor();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("inputFile", "E:/playground/spring-batch-input-file/coffee-list.csv");
        paramsBuilder.addLong("time", System.currentTimeMillis());
        return paramsBuilder.toJobParameters();
    }

    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        final JobExecution jobExec = testUtils.launchJob(defaultJobParameters());
    }

    @Test
    public void testProcess() throws Exception {
        Coffee coffee = new Coffee();
        coffee.setId(1);
        coffee.setStockCount(10);

        CoffeeStock  coffeeStock = coffeeItemProcessor.process(coffee);
        Assertions.assertNotNull(coffeeStock);
        Assertions.assertEquals(12, coffeeStock.getStockCount());

    }
}
