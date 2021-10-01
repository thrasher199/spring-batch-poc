package com.example.springbatchpoc.job.MonthlyDsbQueryReport;

import com.example.springbatchpoc.configuration.DbToDbBatchConfiguration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class MOnthDisQueyStepName extends DbToDbBatchConfiguration {
    @Override
    public ItemProcessor itemProcessor() {
        return null;
    }
}
