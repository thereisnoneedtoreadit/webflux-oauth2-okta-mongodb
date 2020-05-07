package com.example.webfluxmongodb.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.example.webfluxmongodb.exception.NotFoundException;
import com.example.webfluxmongodb.model.actor.NotificationActor;
import com.example.webfluxmongodb.model.dto.CreateTaskRequest;
import com.example.webfluxmongodb.model.entity.Task;
import com.example.webfluxmongodb.repository.TaskRepository;
import com.example.webfluxmongodb.sender.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ActorSystem actorSystem;
    private final MailSender mailSender;

    public Mono<Task> createTask(CreateTaskRequest request, OidcUser user) {
        ActorRef notificationActor = actorSystem.actorOf(
                Props.create(
                        NotificationActor.class,
                        user.getEmail(), request.getName(), request.getDescription(), request.getPerformanceDate(), mailSender
                )
        );
        actorSystem
                .scheduler()
                .scheduleWithFixedDelay(
                        Duration.ZERO,
                        Duration.ofMillis(60 * 1000),
                        notificationActor,
                        "schedule",
                        actorSystem.dispatcher(),
                        ActorRef.noSender());
        return taskRepository.save(new Task(request.getName(), request.getDescription(), request.getPerformanceDate(), user.getName()));
    }

    public Flux<Task> getAllTasks(OidcUser user) {
        return taskRepository.findAllByUser(user.getName());
    }

    public Mono<Task> getOne(String id) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new));
    }

    public Mono<Task> updateTask(Task task) {
        if (task.getId() == null || task.getId().isEmpty())
            throw new IllegalArgumentException("id can't be null or empty");
        return taskRepository.save(task);
    }

    public Mono<Void> deleteTask(String id) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap(taskRepository::delete);
    }

}
