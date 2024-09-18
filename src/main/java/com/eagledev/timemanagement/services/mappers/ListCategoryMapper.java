package com.eagledev.timemanagement.services.mappers;

import com.eagledev.timemanagement.entities.ListCategory;
import com.eagledev.timemanagement.models.listcategory.ListCategoryDto;
import com.eagledev.timemanagement.models.listcategory.ListCategoryPage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListCategoryMapper {
    ListCategoryDto toDto(ListCategory listCategory);
    ListCategoryPage toPageDto(ListCategory listCategory);
}
