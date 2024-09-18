package com.eagledev.timemanagement.services.mappers.config;

import com.eagledev.timemanagement.services.mappers.ListCategoryMapper;
import com.eagledev.timemanagement.services.mappers.TaskMapper;
import com.eagledev.timemanagement.services.mappers.UserMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {ListCategoryMapper.class , TaskMapper.class , UserMapper.class})
public class MapperConfig {
}
