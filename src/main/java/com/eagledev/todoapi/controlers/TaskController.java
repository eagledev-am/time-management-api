package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.exceptions.TaskException;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;
import com.eagledev.todoapi.services.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDtoData> getTask(@PathVariable long id){
        if(id <= 0){
            throw new TaskException("BAD REQUEST" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.getTask(id) , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDtoData> update(@PathVariable long id , @Valid @RequestBody TaskDto dto){
        if(id <= 0){
            throw new TaskException("BAD REQUEST" , HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(service.update(id , dto) , HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable long id){
        if(id <= 0){
            throw new TaskException("BAD REQUEST" , HttpStatus.BAD_REQUEST.value());
        }
        service.removeTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
