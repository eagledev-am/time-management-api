package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface ProjectMemberRepo extends JpaRepository<ProjectMember, Integer> {
    Set<ProjectMember> findProjectMemberByProjectId(int projectId);
    boolean existsProjectMemberByUserIdAndProjectId(int userId ,int projectId);
    ProjectMember findProjectMemberByUserIdAndProjectId(int userId ,int projectId);
}
