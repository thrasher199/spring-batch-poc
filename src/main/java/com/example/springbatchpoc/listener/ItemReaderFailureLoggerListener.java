package com.example.springbatchpoc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.file.FlatFileParseException;

@Slf4j
public class ItemReaderFailureLoggerListener extends ItemListenerSupport {

    public void onReadError(Exception ex) {
        if(ex instanceof FlatFileParseException) {
            FlatFileParseException ffpe = (FlatFileParseException) ex;
            log.error("An error occured while processing the {} line of the file. == {}", ffpe.getLineNumber(), ffpe.getInput());
        } else {
            log.error("An error has occurred", ex);
        }
    }
}
