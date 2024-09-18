package com.eagledev.timemanagement.services.project;

import com.eagledev.timemanagement.entities.*;
import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;
import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.exceptions.ResourceNotFound;
import com.eagledev.timemanagement.models.projectmember.ProjectMemberDto;
import com.eagledev.timemanagement.models.project.ProjectDto;
import com.eagledev.timemanagement.models.project.ProjectPageDto;
import com.eagledev.timemanagement.models.project.ProjectRequest;
import com.eagledev.timemanagement.models.task.TaskAssignRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.repos.ProjectRepo;
import com.eagledev.timemanagement.services.attachment.AttachmentService;
import com.eagledev.timemanagement.services.mappers.ProjectMapper;
import com.eagledev.timemanagement.services.mappers.TaskMapper;
import com.eagledev.timemanagement.services.security.UserContextService;
import com.eagledev.timemanagement.services.task.TaskService;
import com.eagledev.timemanagement.services.projectmember.ProjectMemberService;
import com.eagledev.timemanagement.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService{
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final UserContextService userContextService;
    private final UserService userService;
    private final ProjectMemberService projectMemberService;
    private final TaskMapper taskMapper;
    private final TaskService taskService;
    private final AttachmentService attachmentService;

    @Override
    public Page<ProjectPageDto> getProjects(Pageable pageable) {
        User user = userContextService.getCurrentUser();
        return projectRepo.findAllByMemberId(user.getId() , pageable)
                .map(projectMapper::toPageDto);
    }

    @Override
    public ProjectDto getProjectDetails(int id) {
        return projectMapper.toDto(projectRepo.findById(id)
                .orElseThrow(() ->  new ResourceNotFound("Resource Not found"))
        );

    }

    @Override
    public ProjectDto createProject(ProjectRequest request) {
        User user = userContextService.getCurrentUser();
        Project project = Project.builder()
                .title(request.title())
                .endDate(request.endDate())
                .startDate(request.startDate())
                .description(request.description())
                .project_members(new HashSet<>())
                .attachments(new HashSet<>())
                .taskSet(new HashSet<>())
                .build();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(project)
                .projectRole(ProjectMemberRole.ADMIN)
                .build();
        project.getProject_members().add(projectMember);
        return projectMapper.toDto(projectRepo.save(project));
    }

    // authorize request
    @Override
    public ProjectDto updateProject(int id, ProjectRequest request) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() ->  new ResourceNotFound("Resource Not found"));
        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setEndDate(request.endDate());
        return projectMapper.toDto(projectRepo.save(project));
    }

    // authorize request
    @Override
    public void deleteProject(int id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() ->  new ResourceNotFound("Resource Not found"));
        project.getAttachments()
                .forEach(attachment -> {
                    try {
                        attachmentService.deleteAttachment(attachment);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        project.getTaskSet()
                .forEach(taskService::delete);
        projectRepo.delete(project);
    }

    @Transactional
    @Override
    public String addUserToProject(int projectId, String username ,  ProjectMemberRole projectMemberRole) throws BadRequestException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() ->  new ResourceNotFound("Resource Not found"));
        User user = userService.getUserByUsername(username);
        if(projectMemberService.isMember(user.getId() , projectId)){
            throw new BadRequestException("User already in project");
        }
        ProjectMember projectMember = ProjectMember.builder()
                .projectRole(projectMemberRole)
                .project(project)
                .user(user)
                .build();
        project.getProject_members().add(projectMember);
        projectRepo.save(project);
        return "user added to project successfully";
    }

    @Override
    public String removeUserFromProject(int projectId, String username) throws BadRequestException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() ->  new ResourceNotFound("Resource Not found"));
        User user = userService.getUserByUsername(username);
        if(!projectMemberService.isMember(user.getId() , project.getId())){
            throw new BadRequestException("User not found in this project");
        }
        projectMemberService.removeMember(user , project);
        return "user removed from project successfully";
    }

    @Override
    public String updateUserRole(int projectId, String username, ProjectMemberRole projectRole) throws BadRequestException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not found"));
        User user = userService.getUserByUsername(username);
        if(!projectMemberService.isMember(user.getId() , project.getId())){
            throw new BadRequestException("User not found in this project");
        }
        ProjectMember projectMember = projectMemberService.getMember(user.getId() , project.getId());
        projectMember.setProjectRole(projectRole);
        projectMemberService.save(projectMember);
        return "user role updated successfully";
    }

    @Override
    public Set<ProjectMemberDto> getProjectMembers(int projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() ->  new ResourceNotFound("Resource Not found"));
        return project.getProject_members()
                .stream()
                .map(projectMapper::toProjectMemberDto)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<TaskDto> getTasks(int projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not found"));
        return project.getTaskSet()
                .stream()
                .map(taskMapper::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public TaskDto createTask(int projectId, TaskRequest request) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not found"));
        Task task = taskService.createTask(request);
        task.setProject(project);
        project.getTaskSet().add(task);
        projectRepo.save(project);
        return taskMapper.toModel(task);
    }

    @Override
    public TaskDto getTask(int projectId, int taskId) throws BadRequestException {
        if(projectRepo.existsById(projectId)){
            if(!taskService.isTaskExistInProject(taskId , projectId)){
                throw new BadRequestException("Task not found");
            }
            return taskMapper.toModel(taskService.getTaskById(taskId));
        }
        throw new ResourceNotFound("Resource not found");
    }

    @Transactional
    @Override
    public TaskDto updateTask(int projectId, int taskId , TaskRequest taskRequest) throws BadRequestException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource not found"));

        if(!taskService.isTaskExistInProject(taskId , projectId)){
            throw new BadRequestException("Task not found");
        }

        Task task = taskService.getTaskById(taskId);
        task.setDescription(taskRequest.description());
        task.setDueDate(taskRequest.dueDate());
        task.setTitle(task.getTitle());
        task.setTaskPriority(TaskPriority.valueOf(taskRequest.priority()));

        project.getTaskSet().add(task);
        projectRepo.save(project);
        return taskMapper.toModel(task);
    }

    @Override
    public void deleteTask(int projectId, int taskId) throws BadRequestException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource not found"));
        if(!taskService.isTaskExistInProject(taskId , projectId)){
            throw new BadRequestException("Task not found");
        }
        taskService.deleteTask(taskId);
    }

    @Override
    public String assignTaskToUser(int projectId, TaskAssignRequest request) throws BadRequestException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not found"));
        User user = userService.getUserByUsername(request.username());
        Task task = taskService.getTaskById(request.taskId());

        if(!projectMemberService.isMember(user.getId() , project.getId())){
            throw new BadRequestException("User not found in this project");
        }

        if(!taskService.isTaskExistInProject(task.getId() , project.getId())){
            throw new BadRequestException("Task not found");
        }

        task.setAssignedUser(user);
        project.getTaskSet().add(task);
        projectRepo.save(project);
        return "task assigned to user successfully";
    }

    @Override
    public String uploadNewAttachment(int projectId, MultipartFile file) throws IOException {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not found"));
        User user = userContextService.getCurrentUser();
        if(attachmentService.existsByFileNameAndProjectId(file.getOriginalFilename(), projectId)){
            throw new BadRequestException("File already exists");
        }
        Attachment attachment = attachmentService.createAttachment(file);
        attachment.setUser(user);
        project.getAttachments().add(attachment);
        projectRepo.save(project);
        return "Attachment added successfully";
    }
}
