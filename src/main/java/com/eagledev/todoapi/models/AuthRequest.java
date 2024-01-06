package com.eagledev.todoapi.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRequest {

    @NotBlank(message = "please enter a valid username")
    String username;

    @NotBlank(message = "please enter a valid password")
    String password;
}
