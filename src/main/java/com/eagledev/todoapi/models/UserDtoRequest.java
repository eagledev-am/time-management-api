package com.eagledev.todoapi.models;


import com.eagledev.todoapi.entities.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoRequest {

    long id;

    @NotBlank(message = "this field is required")
    @NotNull
    String userName;

    @NotBlank(message = "this field is required")
    @NotNull
    @Size(min = 4 , message = "The password should have at least four character")
    String password;

    Role role;
}
