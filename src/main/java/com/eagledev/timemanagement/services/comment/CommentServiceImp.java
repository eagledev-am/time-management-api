package com.eagledev.timemanagement.services.comment;

import com.eagledev.timemanagement.entities.Comment;
import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.exceptions.UnAuthorizedException;
import com.eagledev.timemanagement.exceptions.comment.CommentNotFoundException;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.repos.CommentRepo;
import com.eagledev.timemanagement.services.mappers.CommentMapper;
import com.eagledev.timemanagement.services.security.UserContextService;
import com.eagledev.timemanagement.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class CommentServiceImp implements CommentService{
    private CommentRepo commentRepo;
    private UserService userService;
    private CommentMapper commentMapper;
    private UserContextService userContextService;

    @Override
    public CommentModel createComment(CommentRequest comment) {
        User user = userService.getUserById(comment.authorId());
        Comment comment1 = Comment.builder()
                .content(comment.content())
                .createdAt(LocalDateTime.now())
                .replies(new HashSet<>())
                .user(user)
                .build();
        commentRepo.save(comment1);
        return commentMapper.toCommentModel(comment1);
    }

    @Override
    public CommentModel getComment(int id) {
        return commentMapper.toCommentModel(findCommentById(id));
    }

    @Override
    public CommentModel updateComment(int id , CommentRequest request) {
        Comment comment = findCommentById(id);
        User author = comment.getUser();
        User user = userContextService.getCurrentUser();

        if(!author.getUsername().equals(user.getUsername())){
            throw new AccessDeniedException("Unauthorized access");
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
        User user = userContextService.getCurrentUser();

        if(author.getUsername().equals(user.getUsername())){
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
                .replies(new HashSet<>())
                .user(user)
                .build();
        parentComment.getReplies().add(comment);
        return commentMapper.toCommentModel(commentRepo.save(parentComment));
    }

    private Comment findCommentById(int id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

}
