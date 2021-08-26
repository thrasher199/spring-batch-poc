package com.example.springbatchpoc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ItemListenerSupport;

@Slf4j
public class ItemProcessorFailureLoggerListener extends ItemListenerSupport {

    @Override
    public void onProcessError(Object item, Exception e) {
        log.error("Error on processing item {} with exception :- {}",  item, e);
    }
}
