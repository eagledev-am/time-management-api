package com.eagledev.timemanagement.models.comment;

import com.eagledev.timemanagement.models.user.UserModel;

import java.time.LocalDateTime;
import java.util.Set;

public record CommentModel(
        int id ,
        String content ,
        LocalDateTime updatedAt ,
        LocalDateTime createdAt ,
        Set<CommentModel> replies ,
        UserModel user
) {
}
