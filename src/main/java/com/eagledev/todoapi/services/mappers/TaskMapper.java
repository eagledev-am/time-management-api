package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskDto dto);
    TaskDto toDto(Task task);


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creationDate", source = "creationDate"),
            @Mapping(target = "dueDate", source = "dueDate"),
            @Mapping(target = "listCategory" , source = "task.list.category")
    }
    )
    TaskDtoData toDtoData(Task task);


}
