package com.eagledev.timemanagement.models.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRequest {
    @NotNull(message = "please enter a valid username")
    String usernameOrEmail;

    @NotNull(message = "please enter a valid password")
    String password;
}
