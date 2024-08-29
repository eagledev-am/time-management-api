package com.eagledev.todoapi.services.listcategory;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.models.listcategory.ListCategoryDto;
import com.eagledev.todoapi.models.listcategory.ListCategoryRequest;
import com.eagledev.todoapi.models.task.TaskDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ListCategoryServiceImp implements ListCategoryService {

    @Override
    public List<ListCategoryDto> getUserLists() {
        return List.of();
    }

    @Override
    public ListCategory createList(ListCategoryRequest request) {
        return null;
    }

    @Override
    public ListCategory updateList(int listId, ListCategoryRequest request) {
        return null;
    }

    @Override
    public void deleteList() {

    }

    @Override
    public List<TaskDto> getTasksOfList(int listId) {
        return List.of();
    }

    @Override
    public String uploadListImage(int listId, MultipartFile file) {
        return "";
    }
}
