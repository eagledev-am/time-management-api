package com.eagledev.todoapi.services.task;

import com.eagledev.todoapi.entities.Comment;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.models.task.TaskDto;
import com.eagledev.todoapi.models.task.TaskRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class TaskServiceImp implements TaskService {

    @Override
    public Set<TaskDto> getTasksOfAuthenticatedUser() {
        return Set.of();
    }

    @Override
    public Set<TaskDto> getDueDate(int taskId) {
        return Set.of();
    }

    @Override
    public Set<Comment> getTasksComments(int taskId) {
        return Set.of();
    }

    @Override
    public TaskDto getTaskClientById(int id) {
        return null;
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public TaskDto createTask(TaskRequest task) {
        return null;
    }

    @Override
    public TaskDto updateTask(TaskRequest task) {
        return null;
    }

    @Override
    public TaskDto deleteTask(int id) {
        return null;
    }

    @Override
    public String addAttachment(int taskId, MultipartFile file) {
        return "";
    }

    @Override
    public String assignTaskToUser(int taskId, int userId) {
        return "";
    }

    @Override
    public Comment addCommentToTask(int taskId, Comment comment) {
        return null;
    }
}
