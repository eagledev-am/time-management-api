package com.eagledev.timemanagement.services.projectmember;

import com.eagledev.timemanagement.entities.Project;
import com.eagledev.timemanagement.entities.ProjectMember;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;
import com.eagledev.timemanagement.repos.ProjectMemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImp implements ProjectMemberService {
    private final ProjectMemberRepo projectTeamRepo;

    @Override
    public void save(ProjectMember projectMember) {
        projectTeamRepo.save(projectMember);
    }

    @Override
    public void removeMember(User user , Project project) {
        ProjectMember projectMember = projectTeamRepo.findProjectMemberByUserIdAndProjectId(user.getId(), project.getId());
        projectTeamRepo.delete(projectMember);
    }

    @Override
    public List<ProjectMember> getMembersByProjectId(int projectId) {
        return projectTeamRepo.findProjectMemberByProjectId(projectId);
    }

    @Override
    public boolean isMember(int userId, int projectId) {
        return projectTeamRepo.existsProjectMemberByUserIdAndProjectId(userId , projectId);
    }

    @Override
    public ProjectMember getMember(int userId, int projectId) {
        return projectTeamRepo.findProjectMemberByUserIdAndProjectId(userId , projectId);
    }

    @Override
    public ProjectMemberRole getMemberRole(int userId, int projectId) {
        ProjectMember projectMember = projectTeamRepo.findProjectMemberByUserIdAndProjectId(userId , projectId);
        return projectMember != null? projectMember.getProjectRole() : null;
    }
}
