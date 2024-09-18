package com.eagledev.timemanagement.services.task;

import com.eagledev.timemanagement.entities.Task;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.repos.TaskRepo;
import com.eagledev.timemanagement.services.project.ProjectAuthorizationService;
import com.eagledev.timemanagement.services.security.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskAuthorization {
    private final TaskRepo taskRepo;
    private final ProjectAuthorizationService projectAuthorizationService;

    public boolean isOwner(Authentication authentication, int taskId){
        return taskRepo.existsTaskByIdAndOwnerId(taskId, ((User)authentication.getPrincipal()).getId());
    }

    public boolean isAssignedUser(Authentication authentication , int taskId){
        return taskRepo.existsTaskByIdAndAssignedUserId(taskId,((User)authentication.getPrincipal()).getId());
    }

    public boolean isProjectMember(Authentication authentication , int taskId){
        User user = (User)authentication.getPrincipal();
        Optional<Task> taskOptional = taskRepo.findById(taskId);
        if(taskOptional.isPresent()){
            Task task = taskOptional.get();
            if(task.getProject() != null){
                return projectAuthorizationService.canAccessProject(authentication , task.getProject().getId());
            }
        }
        return false;
    }

}
