package com.example.springbatchpoc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class ChunkLogListener extends ChunkListenerSupport {

    @Override
    public void afterChunk(ChunkContext context) {
        int count = context.getStepContext().getStepExecution().getReadCount();
        log.info("Read Count per chunk: " + count);
    }
}
