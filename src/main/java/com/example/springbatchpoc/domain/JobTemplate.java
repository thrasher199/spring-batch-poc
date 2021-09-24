package com.example.springbatchpoc.domain;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobTemplate {
    private String jobName;
    private String inputFilePath;
    private long chunkSize;
    private String cronTab;
    private int jobPriority;
}
