package com.eagledev.timemanagement.services.filestore;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {
    String uploadFile(String uploadPath , MultipartFile file) throws IOException;
    Resource loadFile(String filename) throws FileNotFoundException, MalformedURLException;
    void deleteFile(String filename) throws IOException;
}
