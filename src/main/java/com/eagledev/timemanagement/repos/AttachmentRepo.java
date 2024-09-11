package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttachmentRepo extends JpaRepository<Attachment, Integer> {
    boolean existsByFilename(String originalFilename);

    @Query(nativeQuery = true ,
            value ="select count(*) from attachment  " +
                    "where " +
                    " (filename LIKE %:filename) and task_id = :taskId")
    Integer countByFilenameAndTaskId(@Param("filename") String filename, @Param("taskId") Integer taskId);
}
