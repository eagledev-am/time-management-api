package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.Comment;
import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.models.comment.CommentModel;
import com.eagledev.todoapi.models.user.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    UserModel toUserModel(User user);
    CommentModel toCommentModel(Comment comment);
}
