package com.eagledev.todoapi.services;

import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;

import java.util.List;

public interface TaskService {

    TaskDtoData getTask(long id);
    TaskDtoData update(long id , TaskDto dto);
    TaskDto removeTask(long id);
}
