package com.eagledev.todoapi.services;


import com.eagledev.todoapi.models.ListCategoryDto;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;

import java.util.List;

public interface ListCategoryService {


    List<TaskDtoData> getListCategoryTasks(long id);
    ListCategoryDto removeListCategory(long id);
    TaskDtoData addTaskToList(long id , TaskDto dto);
}
