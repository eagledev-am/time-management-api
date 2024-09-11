package com.eagledev.timemanagement.services.task;

import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.entities.enums.Status;
import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public interface TaskService {
    Set<TaskDto> getTasksOfAuthenticatedUser();
    Set<TaskDto> getDueDate();
    Set<CommentModel> getTasksComments(int taskId);
    TaskDto getTaskByIdClient(int id);
    Task getTaskById(int id);
    TaskDto createTask(TaskRequest task);
    TaskDto updateTask(int id , TaskRequest task);
    void deleteTask(int id);
    String addAttachment(int taskId , MultipartFile file) throws IOException;
    String assignTaskToUser(int taskId, UUID userId) throws BadRequestException;
    CommentModel addCommentToTask(int taskId, CommentRequest comment);
    String updateTaskStatus(int taskId);
    Set<Status> getTasksStatus();
    Set<TaskPriority> getTaskPriorities();
}
