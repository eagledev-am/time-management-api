package com.eagledev.todoapi.models;


import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.entities.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDtoData {
    long id;

    String title;

    String description;


    @Enumerated(EnumType.STRING)
    Status status;

    Date creationDate;

    Date dueDate;

    String listCategory;

}
