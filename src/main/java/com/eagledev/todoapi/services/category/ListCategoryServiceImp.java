package com.eagledev.todoapi.services.category;

import com.eagledev.todoapi.entities.ListCategory;
import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.exceptions.ListCategoryException;
import com.eagledev.todoapi.models.ListCategoryDto;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;
import com.eagledev.todoapi.repos.ListCategoryRepo;
import com.eagledev.todoapi.services.mappers.ListCategoryMapper;
import com.eagledev.todoapi.services.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ListCategoryServiceImp implements ListCategoryService {

    ListCategoryRepo repo;
    ListCategoryMapper mapper;
    TaskMapper taskMapper;

    @Autowired
    public ListCategoryServiceImp(ListCategoryRepo repo , ListCategoryMapper mapper , TaskMapper taskMapper){
        this.repo = repo;
        this.mapper = mapper;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDtoData> getListCategoryTasks(long id) {
      ListCategory listCategory =   repo.findById(id)
              .orElseThrow(()-> new ListCategoryException(ListCategoryException.LIST_CATEGORY_NOT_FOUND , HttpStatus.NOT_FOUND.value()));

      return listCategory.getTasks()
              .stream()
              .map(this::buildTaskDtoData)
              .toList();
    }

    @Override
    public ListCategoryDto removeListCategory(long id) {
        ListCategory category = repo.findById(id)
                .orElseThrow(()-> new ListCategoryException(ListCategoryException.LIST_CATEGORY_NOT_FOUND , HttpStatus.NOT_FOUND.value()));
        repo.delete(category);
        return mapper.toDto(category);
    }

    @Override
    public TaskDtoData addTaskToList(long id , TaskDto dto) {
        ListCategory category = repo.findById(id)
                .orElseThrow(()-> new ListCategoryException(ListCategoryException.LIST_CATEGORY_NOT_FOUND , HttpStatus.NOT_FOUND.value()));
        dto.setCreationDate(new Date());
        Task task = taskMapper.toEntity(dto);
        task.setList(category);
        task.setUser(category.getUser());
        category.getTasks().add(task);
        repo.save(category);
        return buildTaskDtoData(task);
    }

    private TaskDtoData buildTaskDtoData(Task task){
        return TaskDtoData.builder()
                .id(task.getId())
                .description(task.getDescription())
                .title(task.getTitle())
                .dueDate(task.getDueDate())
                .listCategory(task.getList().getCategory())
                .creationDate(task.getCreationDate())
                .status(task.getStatus())
                .build();
    };
}
