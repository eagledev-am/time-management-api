package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.exceptions.TaskException;
import com.eagledev.todoapi.services.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

}
