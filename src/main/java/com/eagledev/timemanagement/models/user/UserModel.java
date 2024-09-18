package com.eagledev.timemanagement.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
public class UserModel {
    @JsonProperty("id")
    private UUID uuid ;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstName ;

    @JsonProperty("lastname")
    private String lastName ;

    @JsonProperty("photo_url")
    private String profilePictureUrl ;

    private String email;
}
