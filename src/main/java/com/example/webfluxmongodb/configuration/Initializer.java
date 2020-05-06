package com.example.webfluxmongodb.configuration;

import com.example.webfluxmongodb.model.entity.Task;
import com.example.webfluxmongodb.repository.TaskRepository;
import com.example.webfluxmongodb.sender.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Date;

@Configuration
@Slf4j
public class Initializer {

    @Autowired
    MailSender mailSender;

    @Bean
    ApplicationRunner init(TaskRepository repository) throws IOException {

        mailSender.send(
                "alyoyona.b@gmail.com",
                "Sending with SendGrid is Fun",
                "and easy to do anywhere, even with Java");

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
                                        return new Task((String) array[0], (String) array[1], new Date(Long.parseLong(array[2].toString())), "username");
                                    })
                                    .flatMap(repository::save)
                    )
                    .thenMany(repository.findAll())
                    .subscribe(kayak -> log.info("saving " + kayak.toString()));
        };
    }

}
