package com.eagledev.timemanagement.services.listcategory;


import com.eagledev.timemanagement.entities.ListCategory;
import com.eagledev.timemanagement.models.listcategory.ListCategoryDto;
import com.eagledev.timemanagement.models.listcategory.ListCategoryPage;
import com.eagledev.timemanagement.models.listcategory.ListCategoryRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ListCategoryService {
    Page<ListCategoryPage> getUserLists(Pageable pageable);
    ListCategoryDto getList(int listId);
    ListCategoryDto createList(ListCategoryRequest request);
    ListCategoryDto updateList(int listId , ListCategoryRequest request);
    void deleteList(int ListId);
    Set<TaskDto> getTasksOfList(int listId);
    TaskDto createTask(int listId , TaskRequest request);
}
