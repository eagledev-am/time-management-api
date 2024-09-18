package com.eagledev.timemanagement.services.comment;


import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;

public interface CommentService {
    CommentModel createComment(CommentRequest comment);
    CommentModel getComment(int id);
    CommentModel updateComment(int id , CommentRequest comment);
    void deleteComment(int id);
    CommentModel replyComment(int parentCommentId , CommentRequest comment);
}
