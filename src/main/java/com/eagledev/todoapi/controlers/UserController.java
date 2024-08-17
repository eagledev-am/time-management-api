package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.repos.UserRepo;
import com.eagledev.todoapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService service;
    UserRepo repo;

    @Autowired
    public UserController(UserService service, UserRepo repo) {
        this.service = service;
        this.repo = repo;
    }
}


