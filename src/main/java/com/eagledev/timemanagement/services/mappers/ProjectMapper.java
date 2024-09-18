package com.eagledev.timemanagement.services.mappers;

import com.eagledev.timemanagement.entities.Project;
import com.eagledev.timemanagement.entities.ProjectMember;
import com.eagledev.timemanagement.models.projectmember.ProjectMemberDto;
import com.eagledev.timemanagement.models.project.ProjectDto;
import com.eagledev.timemanagement.models.project.ProjectPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(source = "taskSet" , target = "tasks")
    ProjectDto toDto(Project project);

    ProjectPageDto toPageDto(Project project);

    @Mappings({
            @Mapping(target = "userId", source = "member.user.uuid"),
            @Mapping(target = "username", source = "member.user.username"),
            @Mapping(target = "firstName", source = "member.user.firstName"),
            @Mapping(target = "lastName", source = "member.user.lastName"),
            @Mapping(target = "role" , source = "projectRole")

    })
    ProjectMemberDto toProjectMemberDto(ProjectMember member);
}
