package com.eagledev.timemanagement.models.task;

import com.eagledev.timemanagement.entities.enums.Status;
import com.eagledev.timemanagement.entities.enums.TaskPriority;

import java.time.Instant;

public record TaskPageDto(
        int id ,
        String title ,
        String description ,
        Status status ,
        TaskPriority taskPriority ,
        Instant creationDate ,
        Instant dueDate
) {
}
