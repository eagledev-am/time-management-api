package com.eagledev.todoapi.services.task;

import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;

public interface TaskService {

    TaskDtoData getTask(long id);
    TaskDtoData update(long id , TaskDto dto);
    TaskDto removeTask(long id);
}
