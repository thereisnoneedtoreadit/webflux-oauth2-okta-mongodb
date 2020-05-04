package com.example.webfluxmongodb.controller;

import com.example.webfluxmongodb.model.dto.Task;
import com.example.webfluxmongodb.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    @PostMapping
    @ResponseBody
    public Mono<Task> create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @GetMapping
    @ResponseBody
    public Flux<Task> getAll() {
        return taskRepository.findAll();
    }

}