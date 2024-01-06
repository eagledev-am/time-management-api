package com.eagledev.todoapi.repos;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User , Long> {

    Optional<User> findUserByUserName(String username);
    @Query(value = "select l from ListCategory l where l.user.id = :id")
    List<ListCategory> getAllCategories(long id);

    @Query(value = "select t  from Task t where t.user.id = :id ")
    List<Task> getAllTasks(long id);

    @Query(value = "select t  from Task t where t.user.id = :id and t.status = :status ")
    List<Task> getAllTasksByStatus(long id , Status status);
}
