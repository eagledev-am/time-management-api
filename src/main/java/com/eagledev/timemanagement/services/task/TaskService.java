package com.eagledev.todoapi.services.task;

import com.eagledev.todoapi.entities.Comment;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.models.task.TaskDto;
import com.eagledev.todoapi.models.task.TaskRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface TaskService {
    Set<TaskDto> getTasksOfAuthenticatedUser();
    Set<TaskDto> getDueDate(int taskId);
    Set<Comment> getTasksComments(int taskId);
    TaskDto getTaskClientById(int id);
    Task getTaskById(int id);
    TaskDto createTask(TaskRequest task);
    TaskDto updateTask(TaskRequest task);
    TaskDto deleteTask(int id);
    String addAttachment(int taskId , MultipartFile file);
    String assignTaskToUser(int taskId, int userId);
    Comment addCommentToTask(int taskId, Comment comment);
}
