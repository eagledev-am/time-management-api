package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.entities.enums.Status;
import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.models.Response;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskPageDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.services.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;


    @Operation(
            tags = {"tasks"} ,
            summary = "GET Tasks" ,
            description = "This endpoint used to get all tasks of authenticated user"
    )
    @GetMapping
    public ResponseEntity<Response<Page<TaskPageDto>>> getAllTasks(@RequestParam(defaultValue = "0") int pageNo , @RequestParam(defaultValue = "25") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo , pageSize , Sort.by("creationDate").descending());
        Response<Page<TaskPageDto>> response = new Response<>(
                "success" , "tasks retrieved successfully" , taskService.getTasksOfAuthenticatedUser(pageable) , null
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "GET Due Tasks" ,
            description = "This endpoint used to get due tasks of authenticated user"
    )
    @GetMapping("/due")
    public ResponseEntity<Response<Page<TaskPageDto>>> getDueDate(@RequestParam(defaultValue = "0") int pageNo , @RequestParam(defaultValue = "25") int pageSize){
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Response<Page<TaskPageDto>> response = new Response<>(
                "success" , "tasks retrieved successfully" , taskService.getDueDate(pageable) , null
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "GET Task by Id" ,
            description = "This endpoint used to get  task by id"
    )
    @PreAuthorize("@taskAuthorization.isOwner(authentication , #id) || @taskAuthorization.isAssignedUser(authentication , #id)")
    @GetMapping("/{id}")
    public ResponseEntity<Response<TaskDto>> getTaskById(@PathVariable int id) {
        Response<TaskDto> response = new Response<>(
                "success" , "tasks retrieved successfully" , taskService.getTaskByIdClient(id) , null
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "GET Task Comments" ,
            description = "This endpoint used to get comments of task"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<Response<Set<CommentModel>>> getTaskComments(@PathVariable int id) {
        Response<Set<CommentModel>> response = new Response<>(
                "success" , "comments retrieved successfully" , taskService.getTasksComments(id) , null
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = {"tasks"} ,
            summary = "POST New Task" ,
            description = "This endpoint used to create new Task"
    )
    @PostMapping
    public ResponseEntity<Response<TaskDto>> createNewTask(@Valid @RequestBody TaskRequest taskRequest) {
        Response<TaskDto> response = new Response<>(
                "success" , "tasks created successfully" , taskService.createTaskClient(taskRequest) , null
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "PUT Task" ,
            description = "This endpoint used to update task"
    )
    @PreAuthorize("@taskAuthorization.isOwner(authentication , #id)")
    @PutMapping("/{id}")
    public ResponseEntity<Response<TaskDto>> updateTask(@PathVariable int id ,@Valid @RequestBody TaskRequest taskRequest) {
        Response<TaskDto> response = new Response<>(
                "success" , "tasks updated successfully" , taskService.updateTask(id , taskRequest) , null
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "DELETE Task by ID" ,
            description = "This endpoint used to update task"
    )
    @PreAuthorize("@taskAuthorization.isOwner(authentication , #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "POST an attachment For Task" ,
            description = "This endpoint used upload an attachment file to task"
    )
    @PreAuthorize("@taskAuthorization.isOwner(authentication , #id) || @taskAuthorization.isAssignedUser(authentication , #id)")
    @PostMapping("/{id}/attachment/upload")
    public ResponseEntity<Response<String>> addAttachmentToTask(@PathVariable int id , @RequestParam(name = "file")MultipartFile file) throws IOException {
        Response<String> response = new Response<>(
                "success" , taskService.addAttachment(id, file) , null , null
        );
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }

    @Operation(
            tags = {"tasks"} ,
            summary = "Add a comment to task" ,
            description = "This endpoint used to add a comment to tasks"
    )
    @PreAuthorize("#request.authorId() == principal.uuid && (@taskAuthorization.isOwner(authentication , #id) || @taskAuthorization.isProjectMember(authentication , #id))")
    @PostMapping(path = "/{id}/comment")
    public ResponseEntity<Response<CommentModel>> addCommentToTask(@PathVariable int id , @Valid @RequestBody CommentRequest request){
        Response<CommentModel> response = new Response<>(
                "success" , "comment saved successfully", taskService.addCommentToTask(id , request) , null
        );
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "update tasks status" ,
            description = "This endpoint used to update status of tasks "
    )
    @PreAuthorize("@taskAuthorization.isOwner(authentication , #id) || @taskAuthorization.isAssignedUser(authentication , #id)")
    @PutMapping(path = "/{id}/status")
    public ResponseEntity<Response<String>> updateTaskStatus(@PathVariable int id ) {
        Response<String> response = new Response<>(
                "success" ,  taskService.updateTaskStatus(id),null , null
        );
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "GET Tasks status Enums" ,
            description = "This endpoint used to get a list of status"
    )
    @GetMapping("/status")
    public ResponseEntity<Set<Status>> getTaskStatuses(){
        return new ResponseEntity<>(taskService.getTasksStatus() , HttpStatus.OK);
    }


    @Operation(
            tags = {"tasks"} ,
            summary = "GET Tasks Priority Enums" ,
            description = "This endpoint used to get a list of priority enums"
    )
    @GetMapping("/priority")
    public ResponseEntity<Set<TaskPriority>> getTaskPriority(){
        return new ResponseEntity<>(taskService.getTaskPriorities() , HttpStatus.OK);
    }

}
