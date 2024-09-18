package com.eagledev.timemanagement.services.attachment;

import com.eagledev.timemanagement.entities.Attachment;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface AttachmentService {
    Attachment createAttachment(MultipartFile file) throws IOException;
    Attachment getAttachment(int id);
    void deleteAttachmentClient(int id) throws IOException;
    void deleteAttachment(Attachment attachment) throws IOException;
    Resource getAttachmentResource(String fileName) throws MalformedURLException, FileNotFoundException;
    Boolean existsByFileNameAndTaskId(String fileName, int taskId);
    Boolean existsByFileNameAndProjectId(String fileName, int taskId);
}
