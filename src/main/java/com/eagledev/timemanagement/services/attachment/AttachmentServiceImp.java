package com.eagledev.timemanagement.services.attachment;

import com.eagledev.timemanagement.entities.Attachment;
import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.exceptions.ResourceNotFound;
import com.eagledev.timemanagement.exceptions.UnAuthorizedException;
import com.eagledev.timemanagement.repos.AttachmentRepo;
import com.eagledev.timemanagement.services.filestore.FileService;
import com.eagledev.timemanagement.services.security.UserContextService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImp implements AttachmentService{

    private final AttachmentRepo attachmentRepo;
    private final FileService fileService;
    private final UserContextService userContextService;

    @Override
    public Attachment createAttachment(MultipartFile file) throws IOException {
        if(file == null){
            throw new BadRequestException("Bad file input");
        }

        String fileName = fileService.uploadFile("attachments/" , file);
        String contentType = file.getContentType();
        String pathUrl = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/api/attachments/file/{filename}")
                .buildAndExpand(fileName)
                .toUri()
                .toString();

        return Attachment.builder()
                .contentType(contentType)
                .filename(fileName)
                .fileUrl(pathUrl)
                .build();
    }

    @Override
    public Attachment getAttachment(int id) {
        return attachmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found"));
    }

    @Transactional
    @Override
    public void deleteAttachmentClient(int id) throws IOException {
        User user = userContextService.getCurrentUser();
        Attachment attachment = getAttachment(id);

        if(attachment.getUser().getId() != user.getId()){
            throw new UnAuthorizedException("You do not have permission to delete this attachment");
        }

        deleteAttachment(attachment);
    }

    @Override
    public void deleteAttachment(Attachment attachment) throws IOException {
        String fileName = attachment.getFilename();
        fileService.deleteFile("attachments/" + fileName);
        attachmentRepo.delete(attachment);
    }

    @Override
    public Resource getAttachmentResource(String fileName) throws MalformedURLException, FileNotFoundException {
        return  fileService.loadFile("attachments/" + fileName);
    }

    @Override
    public Boolean existsByFileNameAndTaskId(String fileName, int taskId) {
        return attachmentRepo.countByFilenameAndTaskId(fileName , taskId) > 0;
    }

    @Override
    public Boolean existsByFileNameAndProjectId(String fileName, int projectId) {
        return attachmentRepo.countByFilenameAndProjectId(fileName , projectId) > 0;
    }
}
