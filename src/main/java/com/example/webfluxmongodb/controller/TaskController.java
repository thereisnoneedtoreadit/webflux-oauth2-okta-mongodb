package com.example.webfluxmongodb.controller;

import com.example.webfluxmongodb.model.dto.CreateTaskRequest;
import com.example.webfluxmongodb.model.entity.Task;
import com.example.webfluxmongodb.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Mono<ResponseEntity<Task>> create(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<List<Task>>> getAll() {
        return taskService.getAllTasks()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task>> getOne(@PathVariable String id) {
        return taskService.getOne(id)
                .map(ResponseEntity::ok);
    }

    @PatchMapping
    public Mono<ResponseEntity<Task>> update(@RequestBody Task task) {
        return taskService.updateTask(task)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return taskService.deleteTask(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

}