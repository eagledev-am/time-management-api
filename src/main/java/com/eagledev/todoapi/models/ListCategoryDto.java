package com.eagledev.todoapi.models;

import com.eagledev.todoapi.entities.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCategoryDto {
    long id;

    @NotNull
    @NotBlank(message = "This filed is required")
    String name;

    @NotNull
    @NotBlank(message = "This filed is required")
    String category;

    String description;

    List<Task> tasks;
}
