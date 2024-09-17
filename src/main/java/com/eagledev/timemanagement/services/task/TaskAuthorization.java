package com.eagledev.timemanagement.services.task;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.repos.TaskRepo;
import com.eagledev.timemanagement.services.security.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class TaskAuthorization {
    private final TaskRepo taskRepo;
    private final UserContextService userContextService;

    public boolean isOwner(Principal principal , int taskId){
        return taskRepo.existsTaskByIdAndOwnerId(taskId, ((User)principal).getId());
    }

    public boolean isAssignedUser(Principal principal , int taskId){
        return taskRepo.existsTaskByIdAndAssignedUserId(taskId,((User)principal).getId());
    }

}
