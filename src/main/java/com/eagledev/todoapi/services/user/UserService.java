package com.eagledev.todoapi.services;

import com.eagledev.todoapi.entities.enums.Status;
import com.eagledev.todoapi.models.*;

import java.util.List;

public interface UserService {
    UserCreationRequest updateUser(long id , UserCreationRequest userDtoRequest);
    void removeUser(long id);
    List<UserCreationRequest> getAllUsers();
    UserDto getUser(long id);
    List<ListCategoryDto> getAllUsersLists(long userId);
    List<TaskDtoData> getAllUserTasks(long userId);
    List<TaskDtoData> getAllUserTasks(long userId , Status status);
    ListCategoryDto createListCategory(long userId , ListCategoryDto categoryDto);
}
