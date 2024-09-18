package com.eagledev.timemanagement.services.project;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;
import com.eagledev.timemanagement.services.projectmember.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectAuthorizationService {
    private final ProjectMemberService service;

    public boolean canAccessProject(Authentication authentication , int projectId){
        User user = (User) authentication.getPrincipal();
        return service.isMember(user.getId(), projectId);
    }

    public boolean canManageProject(Authentication authentication , int projectId){
        User user = (User) authentication.getPrincipal();
        ProjectMemberRole memberRole = service.getMemberRole(user.getId() , projectId);
        return (memberRole == ProjectMemberRole.MANAGER || memberRole == ProjectMemberRole.ADMIN);
    }

    public boolean isAdmin(Authentication authentication , int projectId){
        User user = (User) authentication.getPrincipal();
        ProjectMemberRole memberRole = service.getMemberRole(user.getId() , projectId);
        return memberRole == ProjectMemberRole.ADMIN;
    }

}
