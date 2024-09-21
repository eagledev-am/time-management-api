package com.eagledev.timemanagement.services.comment;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.repos.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAuthorization {
    private final CommentRepo commentRepo ;

    public boolean isCommentOwner(Authentication authentication , int id){
        User user = (User) authentication.getPrincipal();
        return commentRepo.existsByIdAndUserId(id, user.getId());
    }
}
