package com.example.springbatchpoc.integration.download;

import org.aspectj.bridge.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.*;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.integration.sftp.filters.SftpPersistentAcceptOnceFileListFilter;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.io.File;
import java.io.IOException;

//@Configuration
public class SftpFileSyncConfiguration {

    public DefaultSftpSessionFactory sftpSessionFactory(){
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("localhost");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("nasrol");
        factory.setPassword("nasrol");
        return factory;
    }

    @Bean
    public SftpInboundFileSynchronizer synchronizer(){
        SftpInboundFileSynchronizer sync = new SftpInboundFileSynchronizer(sftpSessionFactory());
        sync.setRemoteDirectory("/upload");
        sync.setFilter(compositeFileListFilter());
        sync.setPreserveTimestamp(true);
        return sync;
    }

    @Bean
    @InboundChannelAdapter(channel = "fileUploaded", poller = @Poller(fixedDelay = "10000"))
    public MessageSource<File> sftpMessageSource() throws IOException {
        SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(synchronizer());
        source.setLocalDirectory(new File("G:\\playground\\sftp_data"));
        source.setMaxFetchSize(1);

        ChainFileListFilter<File> chainFileFilter = new ChainFileListFilter<File>();
        chainFileFilter.addFilter(new LastModifiedFileListFilter());
        FileSystemPersistentAcceptOnceFileListFilter fs = new FileSystemPersistentAcceptOnceFileListFilter(new SimpleMetadataStore(), "dailyfilesystem");
        fs.setFlushOnUpdate(true);
        chainFileFilter.addFilter(fs);
        source.setLocalFilter(chainFileFilter);

        return source;
    }

    @ServiceActivator(inputChannel = "fileUploaded")
    public void handleIncomingFile(File file){
        System.out.println("handleIncomingFile" + file.getName());
    }

    @Bean
    public CompositeFileListFilter compositeFileListFilter(){
        ChainFileListFilter chainFileListFilter = new ChainFileListFilter();
        chainFileListFilter.addFilter(new SftpSimplePatternFileListFilter("*.csv"));
        chainFileListFilter.addFilter(new SftpPersistentAcceptOnceFileListFilter(new SimpleMetadataStore(), "sftpMessageSource"));
        return chainFileListFilter;
    }
}
