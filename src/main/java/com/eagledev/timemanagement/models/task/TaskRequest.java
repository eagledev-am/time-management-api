package com.eagledev.timemanagement.models.task;

import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.util.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TaskRequest(

        @NotNull(message = "title is required")
        @Size(min= 1 , max = 100 , message = "title should not exceed 100 char")
        String title ,

        @Size(max = 500 , message = "description should not exceed 500 char")
        String description ,

        @NotNull(message = "task priority is required")
        @ValidEnum(enumClass = TaskPriority.class)
        TaskPriority priority ,

        @Future(message = "Due date should be in the future")
        Instant dueDate
) {

}
