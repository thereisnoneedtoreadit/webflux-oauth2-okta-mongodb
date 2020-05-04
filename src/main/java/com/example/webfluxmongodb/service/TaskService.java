package com.example.webfluxmongodb.service;

import com.example.webfluxmongodb.model.dto.CreateTaskRequest;
import com.example.webfluxmongodb.model.entity.Task;
import com.example.webfluxmongodb.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Mono<Task> createTask(CreateTaskRequest request) {
        return taskRepository.save(new Task(request.getName(), request.getDescription(), request.getPerformanceDate()));
    }

    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

}
