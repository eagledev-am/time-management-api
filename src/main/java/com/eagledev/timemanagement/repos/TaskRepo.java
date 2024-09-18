package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.Project;
import com.eagledev.timemanagement.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Set;

public interface TaskRepo extends JpaRepository<Task , Integer> {
    Page<Task> findAllTasksByOwnerIdOrAssignedUserId(int userId , int assignedUserId , Pageable pageable);
    boolean existsTaskByIdAndOwnerId(int taskId , int ownerId);
    boolean existsTaskByIdAndAssignedUserId(int taskId , int assignedUserId);
    boolean existsTaskByIdAndProjectId(int taskId , int projectId);
    boolean existsTaskByIdAndProject(int taskId , Project project);

    @Query(
            "SELECT t from Task t " +
            "where t.owner.id = :ownerId " +
            "Or t.assignedUser.id = :assignedUserId "
            + "AND t.dueDate >= :dueDate"
    )
    Page<Task> findDueDateTasks(@Param("ownerId") int ownerId , @Param("assignedUserId") int assignedUserId ,@Param("dueDate") Instant dueDate, Pageable pageable);
}
