package com.example.springbatchpoc.configuration;

import org.springframework.batch.item.ItemProcessor;

public abstract class AbstractChunkStepConfiguration {
    protected abstract ItemProcessor itemProcessor();
}
