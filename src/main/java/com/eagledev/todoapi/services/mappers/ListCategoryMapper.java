package com.eagledev.todoapi.services.mappers;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.models.ListCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListCategoryMapper {

    ListCategory toEntity(ListCategoryDto dto);
    ListCategoryDto toDto(ListCategory category);
}
