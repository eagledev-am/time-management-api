package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.entities.enums.Status;
import com.eagledev.todoapi.exceptions.UserException;
import com.eagledev.todoapi.models.*;
import com.eagledev.todoapi.repos.UserRepo;
import com.eagledev.todoapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService service;
    UserRepo repo;

    @Autowired
    public UserController(UserService service , UserRepo repo){
        this.service = service;
        this.repo = repo;
    }


    @GetMapping
    public ResponseEntity<List<UserDtoRequest>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers() , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }

        return new ResponseEntity<>(service.getUser(id) , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoRequest> updateUserDetails(@PathVariable long id , @Valid @RequestBody UserDtoRequest userDtoRequest){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }

        return new ResponseEntity<>(service.updateUser(id , userDtoRequest) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserData(@PathVariable long id){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        service.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/lists")
    public ResponseEntity<ListCategoryDto> createNewList(@PathVariable long id , @Valid @RequestBody ListCategoryDto category){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.createListCategory(id , category) , HttpStatus.OK);
    }

    @GetMapping("/{id}/lists")
    public ResponseEntity<List<ListCategoryDto>> getAllUserLists(@PathVariable long id){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.getAllUsersLists(id) , HttpStatus.OK);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDtoData>> getAllUserTasks(@PathVariable long id){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.getAllUserTasks(id) , HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/tasks" , params = {"status"})
    public ResponseEntity<List<TaskDtoData>> getAllUserTasksByStatus(@PathVariable long id , @RequestParam Status status){
        if(id <= 0){
            throw new UserException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.getAllUserTasks(id , status) , HttpStatus.OK);
    }




}


