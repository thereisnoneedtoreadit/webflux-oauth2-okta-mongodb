package com.example.webfluxmongodb.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateTaskRequest {

    private String name;
    private String description;
    private Date performanceDate;

}
