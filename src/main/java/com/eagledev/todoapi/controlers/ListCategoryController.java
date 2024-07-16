package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.exceptions.ListCategoryException;
import com.eagledev.todoapi.models.ListCategoryDto;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;
import com.eagledev.todoapi.services.category.ListCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ListCategoryController {

    ListCategoryService service;

    @Autowired
    public ListCategoryController(ListCategoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDtoData>> getListTasks(@PathVariable long id){
        if(id <= 0){
            throw new ListCategoryException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.getListCategoryTasks(id) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListCategoryDto> removeList(@PathVariable long id){
        if(id <= 0){
            throw new ListCategoryException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
        service.removeListCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 
    
    @PostMapping("/{id}/tasks")
    public ResponseEntity<TaskDtoData> addTaskToList(@PathVariable long id , @Valid @RequestBody TaskDto taskDto){
        if(id <= 0){
            throw new ListCategoryException("Bad request" , HttpStatus.BAD_REQUEST.value());
        }
       return new ResponseEntity<>(service.addTaskToList(id , taskDto) , HttpStatus.OK); 
    }
}
