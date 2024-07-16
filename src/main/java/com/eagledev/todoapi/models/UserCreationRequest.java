package com.eagledev.todoapi.models;


import com.eagledev.todoapi.entities.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {

    @NotNull(message = "The field USERNAME is required !")
    @Size(max = 20 , message = "user handle length must be less than 20")
    String userName;

    @NotBlank(message = "user password is mandatory")
    @Size(min = 6, max = 20, message = "Password length must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    String password;

    @Email(message = "please enter a valid email !")
    @NotNull(message = "Email field is required !")
    String email;

    @Enumerated(EnumType.STRING)
    Role role;
}
