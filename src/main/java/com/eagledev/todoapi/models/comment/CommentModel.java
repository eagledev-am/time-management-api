package com.eagledev.todoapi.models.comment;

import com.eagledev.todoapi.models.user.UserModel;

import java.time.LocalDateTime;

public record CommentModel(
        int id ,
        String content ,
        LocalDateTime updatedAt ,
        LocalDateTime createdAt ,
        CommentModel parentComment ,
        UserModel user
) {
}
