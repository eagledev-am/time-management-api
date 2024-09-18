package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;
import com.eagledev.timemanagement.models.Response;
import com.eagledev.timemanagement.models.projectmember.ProjectMemberDto;
import com.eagledev.timemanagement.models.project.ProjectDto;
import com.eagledev.timemanagement.models.project.ProjectPageDto;
import com.eagledev.timemanagement.models.project.ProjectRequest;
import com.eagledev.timemanagement.models.task.TaskAssignRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.services.project.ProjectService;
import com.eagledev.timemanagement.util.ValidEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@MultipartConfig
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(
            tags = {"projects"} ,
            summary = "GET Projects Of User" ,
            description = "This endpoint used to get projects of authenticated user"
    )
    @GetMapping
    public ResponseEntity<Page<ProjectPageDto>> getAllProjects(@RequestParam(defaultValue = "0") int pageNo , @RequestParam(defaultValue = "25") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        return new ResponseEntity<>(projectService.getProjects(pageable), HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "GET Project By Id" ,
            description = "This endpoint used to get a project using its id"
    )
    @PreAuthorize(value = "@projectAuthorizationService.canAccessProject(authentication , #id)")
    @GetMapping("/{id}")
    public ResponseEntity<Response<ProjectDto>> getProjectById(@PathVariable int id) {
        Response<ProjectDto> response = new Response<>("success" , "data retrieved successfully" , projectService.getProjectDetails(id), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "POST New Project" ,
            description = "This endpoint used to create a new project"
    )
    @PostMapping
    public ResponseEntity<Response<ProjectDto>> createProject(@RequestBody ProjectRequest projectRequest) {
        Response<ProjectDto> response = new Response<>("success" , "project created successfully" , projectService.createProject(projectRequest) , null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "PUT A Project" ,
            description = "This endpoint used to update project data"
    )
    @PreAuthorize("@projectAuthorizationService.canAccessProject(authentication , #id) && @projectAuthorizationService.canManageProject(authentication , #id)")
    @PutMapping("/{id}")
    public ResponseEntity<Response<ProjectDto>> updateProject(@PathVariable int id, @RequestBody ProjectRequest projectRequest) {
        Response<ProjectDto> response = new Response<>("success" , "project updated successfully" , projectService.updateProject(id , projectRequest) , null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "DELETE A Project" ,
            description = "This endpoint used to delete project data"
    )
    @PreAuthorize("@projectAuthorizationService.isAdmin(authentication , #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "GET Members Of Project" ,
            description = "This endpoint used to get members of project"
    )
    @PreAuthorize("@projectAuthorizationService.canAccessProject(authentication , #id)")
    @GetMapping("/{id}/members")
    public ResponseEntity<Set<ProjectMemberDto>> getProjectMembers(@PathVariable int id) {
        return new ResponseEntity<>(projectService.getProjectMembers(id), HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "POST User To Project" ,
            description = "This endpoint used to enroll user into a project"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @PostMapping("/{id}/members/{username}")
    public ResponseEntity<Response<String>> addUserToProject(@PathVariable int id , @PathVariable String username ,@Valid @ValidEnum(enumClass = ProjectMemberRole.class) @RequestParam("role") String role) throws BadRequestException {
        Response<String> response = new Response<>("success" , projectService.addUserToProject(id , username , ProjectMemberRole.valueOf(role)) , null , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "PUT Project Member Role" ,
            description = "This endpoint used to update project role of User"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @PutMapping("/{id}/members/{username}")
    public ResponseEntity<Response<String>> updateRole(@PathVariable int id , @PathVariable String username , @Valid @ValidEnum(enumClass = ProjectMemberRole.class) @RequestParam("role") String role) throws BadRequestException {
        Response<String> response = new Response<>("success" , projectService.updateUserRole(id , username , ProjectMemberRole.valueOf(role)), null , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "DELETE User From Project" ,
            description = "This endpoint used to remove user from project"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @DeleteMapping("/{id}/members/{username}")
    public ResponseEntity<Response<String>> removeUserFromProject(@PathVariable int id , @PathVariable String username) throws BadRequestException {
        Response<String> response = new Response<>("success"  , projectService.removeUserFromProject(id , username),null , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "GET Project Tasks" ,
            description = "This endpoint used to get tasks of project"
    )
    @PreAuthorize("@projectAuthorizationService.canAccessProject(authentication , #id)")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<Set<TaskDto>> getProjectTasks(@PathVariable int id) {
        return new ResponseEntity<>(projectService.getTasks(id) , HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "GET Project Task " ,
            description = "This endpoint used to get a task"
    )
    @PreAuthorize("@projectAuthorizationService.canAccessProject(authentication , #id)")
    @GetMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<Response<TaskDto>> getProjectTask(@PathVariable int id , @PathVariable int taskId) throws BadRequestException {
        Response<TaskDto> response = new Response<>("success" , "Task retrieved  successfully",projectService.getTask(id , taskId) , null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "POST A New Task In Project" ,
            description = "This endpoint used to create a new task in project"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Response<TaskDto>> createTask(@PathVariable int id , @RequestBody TaskRequest request){
        Response<TaskDto> response = new Response<>("success" , "Task created successfully",projectService.createTask(id , request) , null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            tags = {"projects"} ,
            summary = "PUT A Task In Project" ,
            description = "This endpoint used to update task data"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @PutMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<Response<TaskDto>> updateTask(@PathVariable int id , @PathVariable int taskId , @RequestBody TaskRequest request) throws BadRequestException {
        Response<TaskDto> response = new Response<>("success" , "Task updated successfully",projectService.updateTask(id , taskId , request) , null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            tags = {"projects"} ,
            summary = "DELETE Task In Project" ,
            description = "This endpoint used to delete a task in project"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @DeleteMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable int id , @PathVariable int taskId) throws BadRequestException {
        projectService.deleteTask(id , taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "POST A Task To Member" ,
            description = "This endpoint used to assign task to member"
    )
    @PreAuthorize("@projectAuthorizationService.canManageProject(authentication , #id)")
    @PostMapping("/{id}/tasks/assign")
    public ResponseEntity<Response<String>> assignTaskToMember(@PathVariable int id , @RequestBody TaskAssignRequest taskAssignRequest) throws BadRequestException {
        Response<String> response = new Response<>("success" , projectService.assignTaskToUser(id ,taskAssignRequest), null , null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            tags = {"projects"} ,
            summary = "POST A New Attachment To Project" ,
            description = "This endpoint used to add an attachment to project"
    )
    @PreAuthorize("@projectAuthorizationService.canAccessProject(authentication , #id)")
    @PostMapping("/{id}/attachments")
    public ResponseEntity<Response<String>> uploadAnAttachment(@PathVariable int id , @RequestParam MultipartFile file) throws IOException {
        Response<String> response = new Response<>("success" , projectService.uploadNewAttachment(id , file) , "" , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


}
