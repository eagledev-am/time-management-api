package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.services.filestore.FileService;
import com.eagledev.timemanagement.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    UserService userService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
         String filename = fileService.uploadFile("uploads/" , file);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/file/download/" + filename)
                 .toUriString();
    }

    @GetMapping(path = "/file/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName , HttpServletRequest request) throws IOException {
        Resource resource = fileService.loadFile("uploads/" + fileName);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/profile/Image")
    public ResponseEntity<?> getProfile(@PathVariable String fileName , HttpServletRequest request) throws IOException {
        Resource resource = fileService.loadFile("uploads/" + fileName);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
