package com.example.webfluxmongodb.controller;

import com.example.webfluxmongodb.model.dto.CreateTaskRequest;
import com.example.webfluxmongodb.model.entity.Task;
import com.example.webfluxmongodb.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseBody
    public Mono<Task> create(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    @ResponseBody
    public Flux<Task> getAll() {
        return taskService.getAllTasks();
    }

}