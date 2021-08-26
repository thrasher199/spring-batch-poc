package com.example.springbatchpoc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ItemListenerSupport;

import java.util.List;

@Slf4j
public class ItemWriterFailureLoggerListener extends ItemListenerSupport {

    @Override
    public void onWriteError(Exception ex, List item) {
        item.forEach(x -> {
            log.error("Error on writing item {} with execption -- {}", x, ex);
        });
    }
}
