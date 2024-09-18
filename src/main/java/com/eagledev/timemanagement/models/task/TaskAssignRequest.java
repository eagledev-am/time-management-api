package com.eagledev.timemanagement.models.task;

public record TaskAssignRequest(
        int taskId ,
        String username
) {
}
