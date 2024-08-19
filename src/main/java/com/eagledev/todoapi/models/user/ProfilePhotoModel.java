package com.eagledev.todoapi.models.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequestModel {

    @NotNull(message = "First name is required")
    @Size(message = "size should greater than 2 and less than 50" , min = 2 , max = 50)
    @Pattern(regexp = "^[a-zA-Z-\\s]+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    String firstName;

    @NotNull(message = "Last name  is required")
    @Size(message = "size should greater than 2 and less than 50" , min = 2 , max = 50)
    @Pattern(regexp = "^[a-zA-Z-\\s]+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    String lastName;


    private String email;


    private String profilePictureUrl;
}
