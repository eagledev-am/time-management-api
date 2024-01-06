package com.eagledev.todoapi.models;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    long id;

    @NotBlank(message = "this field is required")
    String title;

    @NotBlank(message = "this field is required")
    String description;


    @Enumerated(EnumType.STRING)
    Status status;

    Date creationDate;

    Date dueDate;

    ListCategory list;

    User user;
}
