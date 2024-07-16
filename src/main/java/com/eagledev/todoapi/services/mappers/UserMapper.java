package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.models.UserCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreationRequest toDto(User user);
}
