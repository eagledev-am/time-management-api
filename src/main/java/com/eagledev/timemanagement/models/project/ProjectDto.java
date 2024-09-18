package com.eagledev.timemanagement.models.project;

import com.eagledev.timemanagement.entities.Attachment;
import com.eagledev.timemanagement.models.task.TaskPageDto;

import java.time.Instant;
import java.util.Set;

public record ProjectDto(
        int id ,
        String title ,
        String description ,
        Instant startDate ,
        Instant endDate ,
        Set<TaskPageDto> tasks ,
        Set<Attachment> attachments
) {
}
