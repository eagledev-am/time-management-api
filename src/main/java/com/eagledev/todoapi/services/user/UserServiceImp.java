package com.eagledev.todoapi.services.user;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.entities.enums.Status;
import com.eagledev.todoapi.exceptions.UserException;
import com.eagledev.todoapi.models.*;
import com.eagledev.todoapi.repos.ListCategoryRepo;
import com.eagledev.todoapi.repos.UserRepo;
import com.eagledev.todoapi.services.mappers.ListCategoryMapper;
import com.eagledev.todoapi.services.mappers.TaskMapper;
import com.eagledev.todoapi.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private UserRepo repo;
    private UserMapper userMapper;
    private PasswordEncoder encoder;
}
