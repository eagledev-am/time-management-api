package com.eagledev.timemanagement.services.task;

import com.eagledev.timemanagement.entities.*;
import com.eagledev.timemanagement.entities.enums.NotificationStatus;
import com.eagledev.timemanagement.entities.enums.Status;
import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.exceptions.ResourceNotFound;
import com.eagledev.timemanagement.exceptions.UnAuthorizedException;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskPageDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.repos.TaskRepo;
import com.eagledev.timemanagement.services.attachment.AttachmentService;
import com.eagledev.timemanagement.services.mappers.CommentMapper;
import com.eagledev.timemanagement.services.mappers.TaskMapper;
import com.eagledev.timemanagement.services.notification.NotificationService;
import com.eagledev.timemanagement.services.project.ProjectService;
import com.eagledev.timemanagement.services.project.ProjectServiceImp;
import com.eagledev.timemanagement.services.projectmember.ProjectMemberService;
import com.eagledev.timemanagement.services.security.UserContextService;
import com.eagledev.timemanagement.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private final NotificationService notificationService;
    private final ProjectMemberService projectMemberService;

    @Override
    public Page<TaskPageDto> getTasksOfAuthenticatedUser(Pageable pageable) {
        User user = userContextService.getCurrentUser();
        return taskRepo.findAllTasksByOwnerIdOrAssignedUserId(user.getId() , user.getId() , pageable)
                .map(mapper::toPageModel);
    }

    @Override
    public Page<TaskPageDto> getDueDate(Pageable pageable) {
        User user = userContextService.getCurrentUser();
         return taskRepo.findDueDateTasks(user.getId() , user.getId() ,Instant.now(),pageable)
                .map(mapper::toPageModel);
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
        return mapper.toModel(task);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource not found"));
    }

    @Transactional
    @Override
    public TaskDto createTaskClient(TaskRequest taskRequest) {
        return mapper.toModel(taskRepo.save(createTask(taskRequest)));
    }

    @Override
    public Task createTask(TaskRequest taskRequest) {
        User owner = userContextService.getCurrentUser();
        return Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .dueDate(taskRequest.dueDate())
                .status(Status.PENDING)
                .taskPriority(taskRequest.priority() == null? TaskPriority.MEDIUM : TaskPriority.valueOf(taskRequest.priority()))
                .creationDate(Instant.now())
                .attachments(new HashSet<>())
                .comments(new HashSet<>())
                .owner(owner)
                .build();
    }

    @Transactional
    @Override
    public TaskDto updateTask(int id, TaskRequest taskRequest) {
        Task task = getTaskById(id);
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setDueDate(taskRequest.dueDate());
        task.setTaskPriority(TaskPriority.valueOf(taskRequest.priority()));

        if(task.getAssignedUser() != null){
          notificationService.sendNotification(
                  notification("The " + task.getTitle() + "has been updated " , getResourceUrl("/api/tasks/" +task.getId())),
                  task.getAssignedUser()
          );
        }

        return mapper.toModel(taskRepo.save(task));
    }

    @Transactional
    @Override
    public void deleteTask(int id) {
        Task task = getTaskById(id);
        User assignedUser = task.getAssignedUser();
        delete(task);
        if(assignedUser != null){
            notificationService.sendNotification(
                    notification("The " + task.getTitle() + "has been deleted" , "" ),
                    task.getAssignedUser()
            );
        }
    }

    @Override
    public void delete(Task task){
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

        if(attachmentService.existsByFileNameAndTaskId(file.getOriginalFilename(), taskId)){
            throw new BadRequestException("File already exists");
        }

        Attachment attachment = attachmentService.createAttachment(file);
        attachment.setUser(user);
        task.getAttachments().add(attachment);
        taskRepo.save(task);

        if(task.getAssignedUser() != null){
            notificationService.sendNotification(
                    notification("A new Attachment has been added to task" + task.getTitle() , attachment.getFileUrl()),
                    task.getAssignedUser()
            );
        }

        return "Attachment has been added successfully";
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

        if(task.getProject() != null) {
            notificationService.sendNotificationsToUsers(
                    notification("A Comment added to Task " + task.getTitle(), getResourceUrl("/api/comments/" + comment1.getId())),
                    projectMemberService.getMembersByProjectId(task.getProject().getId())
            );
        }

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
        notificationService.sendNotification(
                notification("The " + task.getTitle() + "status is updated" , getResourceUrl("/api/tasks/" +task.getId())),
                task.getAssignedUser()
        );

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

    @Override
    public boolean isTaskExistInProject(int taskId , int projectId) {
        return taskRepo.existsTaskByIdAndProjectId(taskId , projectId);
    }

    private Notification notification(String message , String resourceUrl){
        return Notification.builder()
                .message(message)
                .notificationStatus(NotificationStatus.NOT_READ)
                .creationTime(LocalDateTime.now())
                .resourceUrl(resourceUrl)
                .build();
    }

    private String getResourceUrl(String path){
        return ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(path)
                .toUriString();
    }
}
