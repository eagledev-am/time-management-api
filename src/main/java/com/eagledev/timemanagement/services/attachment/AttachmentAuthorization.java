package com.eagledev.timemanagement.services.attachment;

import com.eagledev.timemanagement.entities.Project;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.repos.AttachmentRepo;
import com.eagledev.timemanagement.services.project.ProjectAuthorizationService;
import com.eagledev.timemanagement.services.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentAuthorization {
    private final AttachmentRepo attachmentRepo;
    private final ProjectAuthorizationService projectAuthorizationService;
    private final ProjectService projectService;

    public boolean canManageAttachment(Authentication authentication , String fileName) {
        User user = (User) authentication.getPrincipal();
        if(attachmentRepo.existsByFilename(fileName)){
            if(!isOwner(fileName , user.getId()))
            {
                return isProjectManager(fileName , authentication);
            }
            return true;
        }
        return false;
    }

    public boolean canGetResource(Authentication authentication , String fileName) {
        User user = (User) authentication.getPrincipal();
        if(attachmentRepo.existsByFilename(fileName)){
            if(!isOwner(fileName , user.getId()))
            {
                return isProjectAuthorized(fileName , authentication);
            }
            return true;
        }
        return false;
    }

    boolean isOwner(String fileName , int userId){
        return attachmentRepo.existsByFilenameAndUserId(fileName , userId);
    }

    boolean isProjectAuthorized(String fileName , Authentication authentication){
        if(attachmentRepo.countByFilenameAndProjectNotNull(fileName) > 0) {
            Project project = projectService.getProjectByAttachmentFilename(fileName);
            return project != null && projectAuthorizationService.canAccessProject(authentication, project.getId());
        }
        return false;
    }

    boolean isProjectManager(String fileName , Authentication authentication){
        if(attachmentRepo.countByFilenameAndProjectNotNull(fileName) > 0) {
            Project project = projectService.getProjectByAttachmentFilename(fileName);
            return project != null && projectAuthorizationService.canManageProject(authentication, project.getId());
        }
        return false;
    }
}
