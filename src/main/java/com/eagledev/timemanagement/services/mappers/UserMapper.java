package com.eagledev.timemanagement.services.mappers;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.models.user.UserModel;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel toModel(User user);
}
