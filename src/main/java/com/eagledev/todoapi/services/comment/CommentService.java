package com.eagledev.todoapi.services.comment;

import com.eagledev.todoapi.entities.Comment;
import com.eagledev.todoapi.models.comment.CommentModel;
import com.eagledev.todoapi.models.comment.CommentRequest;

public interface CommentService {
    Comment createComment(CommentRequest comment);
    CommentModel getComment(int id);
    CommentModel updateComment(int id , CommentRequest comment);
    void deleteComment(int id);
    CommentModel replyComment(int parentCommentId , CommentRequest comment);
}
