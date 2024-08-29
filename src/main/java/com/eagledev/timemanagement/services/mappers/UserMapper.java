package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.models.user.UserModel;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel toModel(User user);
}
