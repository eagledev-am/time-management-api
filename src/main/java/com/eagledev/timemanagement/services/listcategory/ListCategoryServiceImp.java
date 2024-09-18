package com.eagledev.timemanagement.services.listcategory;

import com.eagledev.timemanagement.entities.ListCategory;
import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.exceptions.ResourceNotFound;
import com.eagledev.timemanagement.models.listcategory.ListCategoryDto;
import com.eagledev.timemanagement.models.listcategory.ListCategoryPage;
import com.eagledev.timemanagement.models.listcategory.ListCategoryRequest;
import com.eagledev.timemanagement.models.task.TaskDto;
import com.eagledev.timemanagement.models.task.TaskRequest;
import com.eagledev.timemanagement.repos.ListCategoryRepo;
import com.eagledev.timemanagement.services.mappers.ListCategoryMapper;
import com.eagledev.timemanagement.services.mappers.TaskMapper;
import com.eagledev.timemanagement.services.security.UserContextService;
import com.eagledev.timemanagement.services.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListCategoryServiceImp implements ListCategoryService {
    private final ListCategoryRepo listCategoryRepo;
    private final ListCategoryMapper listCategoryMapper;
    private final UserContextService userContextService;
    private final TaskMapper taskMapper;
    private final TaskService taskService;

    @Override
    public Page<ListCategoryPage> getUserLists(Pageable pageable) {
        User user = userContextService.getCurrentUser();
        return listCategoryRepo.findAllByUser(user , pageable)
                .map(listCategoryMapper::toPageDto);
    }

    @Override
    public ListCategoryDto getList(int listId) {
        return listCategoryMapper.toDto(listCategoryRepo.findById(listId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found")));
    }

    @Transactional
    @Override
    public ListCategoryDto createList(ListCategoryRequest request) {
        User user = userContextService.getCurrentUser();
        ListCategory listCategory = ListCategory.builder()
                .creationDate(Instant.now())
                .description(request.description())
                .title(request.title())
                .tasks(new HashSet<>())
                .user(user)
                .build();
        return listCategoryMapper.toDto(listCategoryRepo.save(listCategory));
    }

    @Override
    public ListCategoryDto updateList(int listId, ListCategoryRequest request) {
        ListCategory listCategory = listCategoryRepo.findById(listId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        listCategory.setDescription(request.description());
        listCategory.setTitle(request.title());
        listCategory.setModifiedDate(Instant.now());
        return listCategoryMapper.toDto(listCategoryRepo.save(listCategory));
    }

    @Override
    public void deleteList(int listId) {
        ListCategory listCategory = listCategoryRepo.findById(listId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        listCategory.getTasks()
                .forEach(taskService::delete);
        listCategoryRepo.delete(listCategory);
    }

    @Override
    public Set<TaskDto> getTasksOfList(int listId) {
        ListCategory listCategory = listCategoryRepo.findById(listId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        return listCategory.getTasks()
                .stream().map(taskMapper::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public TaskDto createTask(int listId, TaskRequest request) {
        ListCategory listCategory = listCategoryRepo.findById(listId)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
        Task task = taskService.createTask(request);
        task.setList(listCategory);
        listCategory.getTasks().add(task);
        listCategoryRepo.save(listCategory);
        return taskMapper.toModel(task);
    }
}
