package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttachmentRepo extends JpaRepository<Attachment, Integer> {
    boolean existsByIdAndUserId(int projectId , int userId);

    boolean existsByFilename(String filename);

    boolean existsByFilenameAndUserId(String filename, int userId);

    @Query(nativeQuery = true ,
            value ="select count(*) from attachment  " +
                    "where " +
                    " (filename LIKE %:filename) and task_id = :taskId")
    Integer countByFilenameAndTaskId(@Param("filename") String filename, @Param("taskId") Integer taskId);

    @Query(nativeQuery = true ,
            value ="select count(*) from attachment  " +
                    "where " +
                    " (filename LIKE %:filename) and project_id = :project_id ")
    Integer countByFilenameAndProjectId(@Param("filename") String filename , @Param("project_id") int projectId);


    @Query(nativeQuery = true ,
            value ="select count(*) from attachment  " +
                    "where " +
                    " (filename LIKE %:filename) and project_id is not null limit 1")
    Integer countByFilenameAndProjectNotNull(@Param("filename") String filename);

    Optional<Attachment> findByFilename(String filename);
}
