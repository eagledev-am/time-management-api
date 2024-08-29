package com.eagledev.todoapi.models.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentRequest(

        @NotNull(message = "content field can not be null")
        String content ,

        @NotNull(message = "author Id can not be null")
        UUID authorId
) {
}
