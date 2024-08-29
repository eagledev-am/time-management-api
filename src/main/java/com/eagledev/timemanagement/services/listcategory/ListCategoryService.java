package com.eagledev.todoapi.services.listcategory;


import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.models.listcategory.ListCategoryDto;
import com.eagledev.todoapi.models.listcategory.ListCategoryRequest;
import com.eagledev.todoapi.models.task.TaskDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListCategoryService {
    List<ListCategoryDto> getUserLists();
    ListCategory createList(ListCategoryRequest request);
    ListCategory updateList(int listId , ListCategoryRequest request);
    void deleteList();
    List<TaskDto> getTasksOfList(int listId);
    String uploadListImage(int listId , MultipartFile file);
}
