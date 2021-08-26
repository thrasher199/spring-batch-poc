package com.example.springbatchpoc.job;

import com.example.springbatchpoc.configuration.DbToDbBatchConfiguration;
import com.example.springbatchpoc.configuration.DbToFileBatchConfiguration;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;

public class DbtoFIleJob extends DbToFileBatchConfiguration {
    @Override
    protected ItemProcessor itemProcessor() {
        return null;
    }
}
