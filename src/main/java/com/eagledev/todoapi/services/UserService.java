package com.eagledev.todoapi.services;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.enums.Status;
import com.eagledev.todoapi.models.*;

import java.util.List;

public interface UserService {
    UserDtoRequest updateUser(long id , UserDtoRequest userDtoRequest);
    void removeUser(long id);
    List<UserDtoRequest> getAllUsers();
    UserDto getUser(long id);
    List<ListCategoryDto> getAllUsersLists(long userId);
    List<TaskDtoData> getAllUserTasks(long userId);
    List<TaskDtoData> getAllUserTasks(long userId , Status status);

    ListCategoryDto createListCategory(long userId , ListCategoryDto categoryDto);
}
