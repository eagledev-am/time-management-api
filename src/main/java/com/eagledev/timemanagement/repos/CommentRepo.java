package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment , Integer> {
    boolean existsByIdAndUserId(int commentId , int userId);
}
