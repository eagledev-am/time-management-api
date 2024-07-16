package com.eagledev.todoapi.services.ServiceImp;

import com.eagledev.todoapi.entities.Task;
import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.exceptions.TaskException;
import com.eagledev.todoapi.models.TaskDto;
import com.eagledev.todoapi.models.TaskDtoData;
import com.eagledev.todoapi.repos.TaskRepo;
import com.eagledev.todoapi.services.TaskService;
import com.eagledev.todoapi.services.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImp implements TaskService {

    TaskRepo repo;
    TaskMapper mapper;


    @Autowired
    public TaskServiceImp(TaskRepo repo , TaskMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
    @Override
    public TaskDtoData getTask(long id) {
          Task task = repo.findById(id)
                  .orElseThrow(() -> new TaskException(TaskException.TASK_NOT_FOUND , HttpStatus.NOT_FOUND.value()));
          return buildTaskDtoData(task);
    }


    @Override
    public TaskDtoData update(long id, TaskDto dto) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new TaskException(TaskException.TASK_NOT_FOUND , HttpStatus.NOT_FOUND.value()));

        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setTitle(dto.getTitle());
        task.setDueDate(dto.getDueDate());
        task.setCreationDate(dto.getCreationDate());

        repo.save(task);

        return buildTaskDtoData(task);
    }

    @Override
    public TaskDto removeTask(long id) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new TaskException(TaskException.TASK_NOT_FOUND , HttpStatus.NOT_FOUND.value()));
        repo.delete(task);
        return mapper.toDto(task);
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
    }
}
