package com.example.webfluxmongodb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.util.Date;
import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    private String id;
    private String name;
    private String description;
    private Date creationDate;
    private Date performanceDate;

    public Task(String name, String description, Date performanceDate) {
        this.name = name;
        this.description = description;
        this.creationDate = new Date();
        this.performanceDate = performanceDate;
    }
}
