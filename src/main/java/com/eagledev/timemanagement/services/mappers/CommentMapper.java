package com.eagledev.timemanagement.services.mappers;

import com.eagledev.timemanagement.entities.Comment;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.user.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    UserModel toUserModel(User user);
    CommentModel toCommentModel(Comment comment);
}
