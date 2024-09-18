package com.eagledev.timemanagement.services.projectmember;

import com.eagledev.timemanagement.entities.Project;
import com.eagledev.timemanagement.entities.ProjectMember;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;

import java.util.List;

public interface ProjectMemberService {
    void save(ProjectMember projectMember);
    void removeMember(User user , Project project);
    List<ProjectMember> getMembersByProjectId(int projectId);
    boolean isMember(int userId, int projectId);
    ProjectMember getMember(int userId, int projectId);
    ProjectMemberRole getMemberRole(int userId, int projectId);
}
