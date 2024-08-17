package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.models.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreationRequest toDto(User user);
}
