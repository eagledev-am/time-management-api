package com.eagledev.timemanagement.models.listcategory;

import com.eagledev.timemanagement.models.task.TaskDto;

import java.util.Set;

public record ListCategoryDto(
        int id ,
        String title ,
        String description ,
        String creationDate ,
        String modifiedDate ,
        Set<TaskDto> tasks
) {
}
