package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.models.Response;
import com.eagledev.timemanagement.models.listcategory.ListCategoryDto;
import com.eagledev.timemanagement.models.listcategory.ListCategoryPage;
import com.eagledev.timemanagement.models.listcategory.ListCategoryRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.services.listcategory.ListCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ListCategoryController {
    private final ListCategoryService listCategoryService;

    @Operation(
            tags = {"lists"} ,
            summary = "GET All List Of User" ,
            description = "This endpoint used to get all list of authenticated user ",
            security = @SecurityRequirement(name = "bearerAuth")

    )
    @GetMapping
    public ResponseEntity<Page<ListCategoryPage>> getListOfUser(@RequestParam(defaultValue = "0") int pageNo , @RequestParam(defaultValue = "25") int pageSize){
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        return new ResponseEntity<>(listCategoryService.getUserLists(pageable) , HttpStatus.OK);
    }


    @Operation(
            tags = {"lists"} ,
            summary = "GET Specified List " ,
            description = "This endpoint used to get all data of list using id of list ",
            security = @SecurityRequirement(name = "bearerAuth")

    )
    @PreAuthorize("@listCategoryAuthorization.isListOwner(authentication , #id)")
    @GetMapping("/{id}")
    public ResponseEntity<Response<ListCategoryDto>> getList(@PathVariable int id){
        Response<ListCategoryDto> response = new Response<>("success" , "list retrieved successfully", listCategoryService.getList(id) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"lists"} ,
            summary = "POST A New List " ,
            description = "This endpoint used to create a new list",
            security = @SecurityRequirement(name = "bearerAuth")

    )
    @PostMapping
    public ResponseEntity<Response<ListCategoryDto>> createList(@RequestBody ListCategoryRequest request){
        Response<ListCategoryDto> response = new Response<>("success" , "list created successfully", listCategoryService.createList(request) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"lists"} ,
            summary = "Update Specified List " ,
            description = "This endpoint used to update data of specified list",
            security = @SecurityRequirement(name = "bearerAuth")

    )
    @PreAuthorize("@listCategoryAuthorization.isListOwner(authentication , #id)")
    @PutMapping("/{id}")
    public ResponseEntity<Response<ListCategoryDto>> updateList(@PathVariable int id , @RequestBody ListCategoryRequest request){
        Response<ListCategoryDto> response = new Response<>("success" , "list updated successfully", listCategoryService.updateList(id , request) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"lists"} ,
            summary = "DELETE Specified List " ,
            description = "This endpoint used to delete data of list" ,
            security = @SecurityRequirement(name = "bearerAuth")

    )
    @PreAuthorize("@listCategoryAuthorization.isListOwner(authentication , #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteList(@PathVariable int id){
        listCategoryService.deleteList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            tags = {"lists"} ,
            summary = "GET Tasks Of List " ,
            description = "This endpoint used to get all tasks of list using id of list " ,
            security = @SecurityRequirement(name = "bearerAuth")

    )
    @PreAuthorize("@listCategoryAuthorization.isListOwner(authentication , #id)")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<Response<Set<TaskDto>>> getTaskSet(@PathVariable int id , @RequestBody ListCategoryRequest request){
        Response<Set<TaskDto>> response = new Response<>("success" , "task retrieved successfully", listCategoryService.getTasksOfList(id) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @Operation(
            tags = {"lists" , "tasks"} ,
            summary = "Create A Task In List" ,
            description = "This endpoint used to create a task in list"
            ,security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("@listCategoryAuthorization.isListOwner(authentication , #id)")
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Response<TaskDto>> createTask(@PathVariable int id , @RequestBody TaskRequest request){
        Response<TaskDto> response = new Response<>("success" , "task created successfully", listCategoryService.createTask(id , request) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @Operation(
            tags = {"lists" , "tasks"} ,
            summary = "Enroll A Task In List" ,
            description = "This endpoint used to enroll a task in list"
            ,security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("@listCategoryAuthorization.isListOwner(authentication , #listId) && (@taskAuthorization.isOwner(authentication , #taskId) || @taskAuthorization.isAssignedUser(authentication , #taskId))")
    @PostMapping("/{listId}/tasks/{taskId}")
    public ResponseEntity<Response<TaskDto>> enrollTask(@PathVariable int listId , @PathVariable int taskId , @RequestBody TaskRequest request){
        Response<TaskDto> response = new Response<>("success" , "task enrolled successfully", listCategoryService.enrollTask(listId , taskId , request) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

}
