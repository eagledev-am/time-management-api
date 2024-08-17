package com.eagledev.todoapi.models.user;

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
    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime dateJoined;

    private String profilePictureUrl;
}
