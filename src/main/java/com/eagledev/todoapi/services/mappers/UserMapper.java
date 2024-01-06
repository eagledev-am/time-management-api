package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.models.UserDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id" , source = "id") ,
            @Mapping(target = "password" , source = "password") ,
            @Mapping(target = "role" , source = "role") ,
    })
    UserDtoRequest toDto(User user);

}
