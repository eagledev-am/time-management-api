package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProjectRepo extends JpaRepository<Project, Integer> {
    @Query(
            "select p FROM Project p " +
                    "JOIN ProjectMember pt " +
                    "ON p.id = pt.project.id "+
                    "WHERE pt.user.id = :id"
    )
    Page<Project> findAllByMemberId(@Param("id") int id , Pageable pageable);

    @Query(
            nativeQuery = true ,
            value = "select * from project " +
            "Join attachment " +
            "on attachment.project_id = project.id " +
            "Where attachment.id = :id"
    )
    Project findByAttachmentId(@Param("id") int attachmentId);

    @Query(
            value = "SELECT p.* " +
            " FROM project p " +
            " JOIN attachment a ON p.id = a.project_id " +
            "WHERE a.filename = :filename"
            , nativeQuery = true
    )
    Project findByAttachmentFileName(@Param("filename") String filename);
}
