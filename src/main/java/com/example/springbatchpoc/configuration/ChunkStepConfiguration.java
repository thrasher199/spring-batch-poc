package com.example.springbatchpoc.configuration;

import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;

public interface ChunkStepConfiguration {
    public ItemProcessor itemProcessor();
    public SimpleStepBuilder createBaseStep();
}
