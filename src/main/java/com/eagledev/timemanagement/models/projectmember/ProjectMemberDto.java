package com.eagledev.timemanagement.models.projectmember;

import com.eagledev.timemanagement.entities.enums.ProjectMemberRole;

import java.util.UUID;

public record ProjectMemberDto(
        UUID userId ,
        String username ,
        String firstName ,
        String lastName ,
        ProjectMemberRole role ,
        String profileImage
) {
}
