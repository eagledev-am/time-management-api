package com.eagledev.timemanagement.models.comment;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequest(

        @NotNull(message = "content field can not be null")
        String content ,

        @NotNull(message = "author Id can not be null")
        UUID authorId
) {
}
