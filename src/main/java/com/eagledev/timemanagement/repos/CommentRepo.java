package com.eagledev.todoapi.repos;

import com.eagledev.todoapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment , Integer> {
}
