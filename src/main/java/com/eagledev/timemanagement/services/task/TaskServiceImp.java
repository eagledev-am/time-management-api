package com.eagledev.timemanagement.services.task;

import com.eagledev.timemanagement.entities.Attachment;
import com.eagledev.timemanagement.entities.Comment;
import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.Status;
import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.exceptions.ResourceNotFound;
import com.eagledev.timemanagement.exceptions.UnAuthorizedException;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.repos.TaskRepo;
import com.eagledev.timemanagement.services.attachment.AttachmentService;
import com.eagledev.timemanagement.services.mappers.CommentMapper;
import com.eagledev.timemanagement.services.mappers.TaskMapper;
import com.eagledev.timemanagement.services.security.UserContextService;
import com.eagledev.timemanagement.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {

    private final TaskRepo taskRepo;
    private final UserContextService userContextService;
    private final TaskMapper mapper;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final AttachmentService attachmentService;

    @Override
    public Set<TaskDto> getTasksOfAuthenticatedUser() {
        User user = userContextService.getCurrentUser();
        return taskRepo.findAllTasksByOwnerIdOrAssignedUserId(user.getId() , user.getId())
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TaskDto> getDueDate() {
        User user = userContextService.getCurrentUser();
        return taskRepo.findAllTasksByOwnerIdOrAssignedUserId(user.getId() , user.getId())
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CommentModel> getTasksComments(int taskId) {
        Task task = getTaskById(taskId);
        return task.getComments()
                .stream()
                .map(commentMapper::toCommentModel)
                .collect(Collectors.toSet());
    }

    @Override
    public TaskDto getTaskByIdClient(int id) {
        Task task = getTaskById(id);
        User user = userContextService.getCurrentUser();

        if(!task.getOwner().getUsername().equals(user.getUsername())
        && !(task.getAssignedUser() != null && task.getAssignedUser().getUsername().equals(user.getUsername())))
        {
            throw new UnAuthorizedException("UnAuthorized access");
        }

        return mapper.toModel(task);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource not found"));
    }

    @Transactional
    @Override
    public TaskDto createTask(TaskRequest taskRequest) {
        User owner = userContextService.getCurrentUser();
        Task task = Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .dueDate(taskRequest.dueDate())
                .status(Status.PENDING)
                .taskPriority(taskRequest.priority() == null? TaskPriority.MEDIUM : taskRequest.priority())
                .creationDate(Instant.now())
                .attachments(new HashSet<>())
                .comments(new HashSet<>())
                .owner(owner)
                .build();
        return mapper.toModel(taskRepo.save(task));
    }

    @Override
    public TaskDto updateTask(int id, TaskRequest taskRequest) {
        User user = userContextService.getCurrentUser();
        Task task = getTaskById(id);

        if(task.getOwner().getId() != user.getId()){
            throw new UnAuthorizedException("UnAuthorized access");
        }

        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setDueDate(taskRequest.dueDate());
        task.setTaskPriority(taskRequest.priority());

        return mapper.toModel(taskRepo.save(task));
    }


    @Override
    public void deleteTask(int id) {
        User user = userContextService.getCurrentUser();
        Task task = getTaskById(id);

        if(task.getOwner().getId() != user.getId()){
            throw new UnAuthorizedException("UnAuthorized access");
        }

        task.getAttachments()
                .forEach(attachment -> {
                    try {
                        attachmentService.deleteAttachment(attachment);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        taskRepo.delete(task);
    }

    @Transactional
    @Override
    public String addAttachment(int taskId, MultipartFile file) throws IOException {
        User user = userContextService.getCurrentUser();
        Task task = getTaskById(taskId);

        if(!task.getOwner().getUsername().equals(user.getUsername())
                && !(task.getAssignedUser() != null && task.getAssignedUser().getUsername().equals(user.getUsername())))
        {
            throw new UnAuthorizedException("UnAuthorized access");
        }

        if(attachmentService.existsByFileNameAndTaskId(file.getOriginalFilename(), taskId)){
            throw new BadRequestException("File already exists");
        }

        Attachment attachment = attachmentService.createAttachment(file);
        attachment.setUser(user);

        task.getAttachments().add(attachment);
        taskRepo.save(task);
        return "Attachment has been added successfully";
    }

    @Override
    public String assignTaskToUser(int taskId, UUID userId) throws BadRequestException {
        User user = userContextService.getCurrentUser();
        Task task = getTaskById(taskId);
        User assignedUser = userService.getUserById(userId);

        if(!task.getOwner().getUuid().equals(user.getUuid())){
                throw new UnAuthorizedException("UnAuthorized access");
        }

        if(task.getOwner().getUuid().equals(userId)){
            throw new BadRequestException("Can not assign task to yourself!");
        }

        task.setAssignedUser(assignedUser);
        taskRepo.save(task);
        return "task has been assigned to user successfully";
    }

    @Override
    public CommentModel addCommentToTask(int taskId, CommentRequest comment) {
        Task task = getTaskById(taskId);
        User user = userService.getUserById(comment.authorId());
        Comment comment1 = Comment.builder()
                .content(comment.content())
                .createdAt(LocalDateTime.now())
                .replies(new HashSet<>())
                .user(user)
                .build();
        task.getComments().add(comment1);
        taskRepo.save(task);
        return commentMapper.toCommentModel(comment1);
    }

    @Override
    public String updateTaskStatus(int taskId) {
        Task task = getTaskById(taskId);
        User user = userContextService.getCurrentUser();

        if(!task.getOwner().getUsername().equals(user.getUsername())
                && !(task.getAssignedUser() != null && task.getAssignedUser().getUsername().equals(user.getUsername())))
        {
            throw new UnAuthorizedException("UnAuthorized access");
        }

        task.setStatus((task.getStatus() == Status.COMPLETED)? Status.NOT_COMPLETED : Status.COMPLETED);
        taskRepo.save(task);
        return "tasks status has been updated successfully";
    }

    @Override
    public Set<Status> getTasksStatus() {
        return Arrays.stream(Status.values()).collect(Collectors.toSet());
    }

    @Override
    public Set<TaskPriority> getTaskPriorities() {
        return Arrays.stream(TaskPriority.values()).collect(Collectors.toSet());
    }

}
