package com.eagledev.todoapi.repos;

import com.eagledev.todoapi.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task , Long> {
}
