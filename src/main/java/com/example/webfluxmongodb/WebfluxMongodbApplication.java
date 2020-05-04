package com.example.webfluxmongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoRepositories
@Slf4j
public class WebfluxMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxMongodbApplication.class, args);
    }

}
