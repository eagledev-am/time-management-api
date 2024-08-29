package com.eagledev.todoapi.services.comment;

import com.eagledev.todoapi.entities.Comment;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.exceptions.UnAuthorizedException;
import com.eagledev.todoapi.exceptions.comment.CommentNotFoundException;
import com.eagledev.todoapi.models.comment.CommentModel;
import com.eagledev.todoapi.models.comment.CommentRequest;
import com.eagledev.todoapi.repos.CommentRepo;
import com.eagledev.todoapi.services.mappers.CommentMapper;
import com.eagledev.todoapi.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImp implements CommentService{
    private CommentRepo commentRepo;
    private UserService userService;
    private CommentMapper commentMapper;

    @Override
    public Comment createComment(CommentRequest comment) {
        User user = userService.getUserById(comment.authorId());
        Comment comment1 = Comment.builder()
                .content(comment.content())
                .createdAt(LocalDateTime.now())
                .comment(null)
                .user(user)
                .build();
        commentRepo.save(comment1);
        return comment1;
    }

    @Override
    public CommentModel getComment(int id) {
        return commentMapper.toCommentModel(findCommentById(id));
    }

    @Override
    public CommentModel updateComment(int id , CommentRequest request) {
        Comment comment = findCommentById(id);
        User author = comment.getUser();

        if(author.getUsername().equals(getAuthenticatedUser().getUsername())){
            throw new UnAuthorizedException("Unauthorized access");
        }

        comment.setContent(request.content());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepo.save(comment);
        return commentMapper.toCommentModel(comment);
    }

    @Override
    public void deleteComment(int id) {
        Comment comment = findCommentById(id);
        User author = comment.getUser();
        if(author.getUsername().equals(getAuthenticatedUser().getUsername())){
            throw new UnAuthorizedException("Unauthorized access");
        }
        commentRepo.delete(comment);
    }

    @Override
    public CommentModel replyComment(int parentId, CommentRequest request) {
        Comment parentComment = findCommentById(parentId);
        User user = userService.getUserById(request.authorId());
        Comment comment = Comment.builder()
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .comment(parentComment)
                .user(user)
                .build();
        return commentMapper.toCommentModel(commentRepo.save(comment));
    }

    private Comment findCommentById(int id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    private User getAuthenticatedUser(){
        return (User)((Principal)SecurityContextHolder.getContext().getAuthentication());
    }
}
