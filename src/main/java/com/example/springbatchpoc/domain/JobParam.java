package com.example.springbatchpoc.domain;

import lombok.Builder;
import org.springframework.data.repository.CrudRepository;

@Builder
public class JobParam {
    private Class type;
    private String[] names;
    private CrudRepository repository;
    private String methodName;
    private int chunkSize;
}
