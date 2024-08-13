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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    UserRepo repo;
    ListCategoryRepo listCategoryRepo;
    UserMapper mapper;
    TaskMapper taskMapper;
    ListCategoryMapper categoryMapper;
    PasswordEncoder encoder;

    @Autowired
    public UserServiceImp(UserRepo repo, UserMapper mapper, TaskMapper taskMapper, ListCategoryMapper categoryMapper , ListCategoryRepo listCategoryRepo , PasswordEncoder encoder) {
        this.repo = repo;
        this.mapper = mapper;
        this.taskMapper = taskMapper;
        this.categoryMapper = categoryMapper;
        this.listCategoryRepo = listCategoryRepo;
        this.encoder = encoder;
    }

    @Override
    public UserCreationRequest updateUser(long id, UserCreationRequest userDto) {
        User user= repo.findById(id)
                .orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value()));

        user.setUserName(userDto.getUserName());
        user.setPassword(encoder.encode(userDto.getPassword()));

        repo.save(user);

        return mapper.toDto(user);
    }

    @Override
    public void removeUser(long id) {
        User user= repo.findById(id)
                .orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value()));
        repo.delete(user);
    }

    @Override
    public List<UserCreationRequest> getAllUsers() {
        List<User> users= repo.findAll();

        if(users.isEmpty()){
            throw new UserException("THERE IS NOT USERS" , HttpStatus.NOT_FOUND.value());
        }

        return users.stream()
                .map(user -> mapper.toDto(user))
                .toList();
    }

    @Override
    public UserDto getUser(long id) {
        User user= repo.findById(id)
                .orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value()));

        List<ListCategory> list = repo.getAllCategories(id);

        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .categories(list)
                .build();
    }

    @Override
    public List<ListCategoryDto> getAllUsersLists(long userId) {
        if(!repo.existsById(userId)){
            throw new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value());
        }
        return repo.getAllCategories(userId)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public List<TaskDtoData> getAllUserTasks(long userId) {
        if(!repo.existsById(userId)){
            throw new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value());
        }
        return repo.getAllTasks(userId)
                .stream()
                .map(taskMapper::toDtoData)
                .toList();
    }

    @Override
    public List<TaskDtoData> getAllUserTasks(long userId, Status status) {
        if(!repo.existsById(userId)){
            throw new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value());
        }
        return repo.getAllTasksByStatus(userId , status)
                .stream()
                .map(taskMapper::toDtoData)
                .toList();
    }

    @Override
    public ListCategoryDto createListCategory(long userId , ListCategoryDto categoryDto) {
        User user= repo.findById(userId)
                .orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND , HttpStatus.NOT_FOUND.value()));
        ListCategory category = categoryMapper.toEntity(categoryDto);
        category.setUser(user);
        listCategoryRepo.save(category);
        return categoryMapper.toDto(category);
    }
}
