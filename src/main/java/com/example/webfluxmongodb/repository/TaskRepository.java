package com.example.webfluxmongodb.repository;

import com.example.webfluxmongodb.model.dto.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, UUID> {
}