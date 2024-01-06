package com.eagledev.todoapi.services.mappers.config;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.services.mappers.ListCategoryMapper;
import com.eagledev.todoapi.services.mappers.TaskMapper;
import com.eagledev.todoapi.services.mappers.UserMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {ListCategoryMapper.class , TaskMapper.class , UserMapper.class})
public class MapperConfig {
}
