package com.example.webfluxmongodb;

import com.example.webfluxmongodb.model.dto.Task;
import com.example.webfluxmongodb.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoRepositories
@Slf4j
public class WebfluxMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxMongodbApplication.class, args);
    }

    @Bean
    ApplicationRunner init(TaskRepository repository) {

        Object[][] data = {
                {"task1", "desc1"},
                {"task2", "desc2"},
                {"task3", "desc3"},
        };

        return args -> {
            repository
                    .deleteAll()
                    .thenMany(
                            Flux
                                    .just(data)
                                    .map(array -> {
                                        return new Task((String) array[0], (String) array[1]);
                                    })
                                    .flatMap(repository::save)
                    )
                    .thenMany(repository.findAll())
                    .subscribe(kayak -> log.info("saving " + kayak.toString()));
        };
    }

}
