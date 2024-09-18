package com.eagledev.timemanagement.services.project;

import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;
import com.eagledev.timemanagement.models.projectmember.ProjectMemberDto;
import com.eagledev.timemanagement.models.project.ProjectDto;
import com.eagledev.timemanagement.models.project.ProjectPageDto;
import com.eagledev.timemanagement.models.project.ProjectRequest;
import com.eagledev.timemanagement.models.task.TaskAssignRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface ProjectService {
    Page<ProjectPageDto> getProjects(Pageable pageable);
    ProjectDto getProjectDetails(int id);
    ProjectDto createProject(ProjectRequest request);
    ProjectDto updateProject(int id , ProjectRequest request);
    void deleteProject(int id);
    String addUserToProject(int projectId , String username , ProjectMemberRole projectMemberRole) throws BadRequestException;
    String removeUserFromProject(int projectId , String username) throws BadRequestException;
    Set<ProjectMemberDto> getProjectMembers(int projectId);
    String updateUserRole(int projectId , String username , ProjectMemberRole projectRole) throws BadRequestException;
    Set<TaskDto> getTasks(int projectId);
    TaskDto createTask(int projectId , TaskRequest request);
    TaskDto getTask(int projectId , int taskId) throws BadRequestException;
    TaskDto updateTask(int projectId , int taskId, TaskRequest taskRequest) throws BadRequestException;
    void deleteTask(int projectId , int taskId) throws BadRequestException;
    String assignTaskToUser(int projectId , TaskAssignRequest taskAssignRequest) throws BadRequestException;
    String uploadNewAttachment(int projectId , MultipartFile file) throws IOException;
}
