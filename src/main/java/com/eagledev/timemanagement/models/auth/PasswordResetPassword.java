package com.eagledev.timemanagement.models.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PasswordResetPassword {

    @NotNull(message = "This field is required !")
    String emailOrUserName;

    @NotNull(message = "Password field is required !")
    @Size(min = 6, max = 20, message = "Password length must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    String newPassword;

    String code;
}
