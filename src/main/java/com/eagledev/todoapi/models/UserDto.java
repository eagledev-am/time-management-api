package com.eagledev.todoapi.models;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    long id;

    String userName;

    String password;

    Role role;

    List<ListCategory> categories;
}
