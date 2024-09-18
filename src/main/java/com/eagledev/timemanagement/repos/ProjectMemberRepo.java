package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.Project;
import com.eagledev.timemanagement.entities.ProjectMember;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepo extends JpaRepository<ProjectMember, Integer> {
    List<ProjectMember> findProjectMemberByProjectId(int projectId);
    boolean existsProjectMemberByUserIdAndProjectId(int userId ,int projectId);
    ProjectMember findProjectMemberByUserIdAndProjectId(int userId ,int projectId);
}
