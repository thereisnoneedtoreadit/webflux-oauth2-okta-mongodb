package com.example.webfluxmongodb.configuration;

import com.example.webfluxmongodb.model.entity.Task;
import com.example.webfluxmongodb.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.Date;

@Configuration
@Slf4j
public class Initializer {

    @Bean
    ApplicationRunner init(TaskRepository repository) {

        Object[][] data = {
                {"task1", "desc1", "1588603894077"},
                {"task2", "desc2", "1588603894077"},
                {"task3", "desc3", "1588603894077"},
        };

        return args -> {
            repository
                    .deleteAll()
                    .thenMany(
                            Flux
                                    .just(data)
                                    .map(array -> {
                                        return new Task((String) array[0], (String) array[1], new Date(Long.valueOf(array[2].toString())));
                                    })
                                    .flatMap(repository::save)
                    )
                    .thenMany(repository.findAll())
                    .subscribe(kayak -> log.info("saving " + kayak.toString()));
        };
    }

}
