package com.eagledev.timemanagement.models.task;

import com.eagledev.timemanagement.entities.Attachment;
import com.eagledev.timemanagement.entities.Comment;
import com.eagledev.timemanagement.entities.enums.Status;
import com.eagledev.timemanagement.entities.enums.TaskPriority;
import com.eagledev.timemanagement.models.attachment.AttachmentModel;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.user.UserModel;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record TaskDto(
        int id ,
        String title ,
        String description ,
        Status status ,
        TaskPriority taskPriority ,
        Instant creationDate ,
        Instant dueDate ,
        UserModel owner ,
        UserModel assignedUser ,
        Set<AttachmentModel>attachments ,
        Set<CommentModel>comments
) {
}
