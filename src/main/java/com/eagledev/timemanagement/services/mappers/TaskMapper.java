package com.eagledev.timemanagement.services.mappers;

import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskPageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toModel(Task task);
    TaskPageDto toPageModel(Task task);
}
