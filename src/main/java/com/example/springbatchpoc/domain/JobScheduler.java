package com.example.springbatchpoc.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class JobScheduler {
    private String schedulerName;
    private String cronTab;
    private Set<JobTemplate> jobTemplateSet;
}
