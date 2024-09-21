package com.eagledev.timemanagement.services.comment;

import com.eagledev.timemanagement.entities.Comment;
import com.eagledev.timemanagement.entities.Notification;
import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.NotificationStatus;
import com.eagledev.timemanagement.exceptions.UnAuthorizedException;
import com.eagledev.timemanagement.exceptions.comment.CommentNotFoundException;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.repos.CommentRepo;
import com.eagledev.timemanagement.services.mappers.CommentMapper;
import com.eagledev.timemanagement.services.notification.NotificationService;
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
    private NotificationService notificationService;


    @Override
    public CommentModel getComment(int id) {
        return commentMapper.toCommentModel(findCommentById(id));
    }

    @Override
    public CommentModel updateComment(int id , CommentRequest request) {
        Comment comment = findCommentById(id);
        comment.setContent(request.content());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepo.save(comment);
        return commentMapper.toCommentModel(comment);
    }

    @Override
    public void deleteComment(int id) {
        Comment comment = findCommentById(id);
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
        Comment reply = commentRepo.save(parentComment);
        notificationService.sendNotification(
                notification("A new you reply to you comment by " + user.getUsername() , "/api/comment/" + reply.getId()) ,
                userContextService.getCurrentUser()
        );
        return commentMapper.toCommentModel(reply);
    }

    private Comment findCommentById(int id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    private Notification notification(String message , String resourceUrl){
        return Notification.builder()
                .message(message)
                .notificationStatus(NotificationStatus.NOT_READ)
                .creationTime(LocalDateTime.now())
                .resourceUrl(resourceUrl)
                .build();
    }
}
