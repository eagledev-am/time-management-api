package com.eagledev.todoapi.services.filestore;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {
    String uploadFile(String uploadPath , MultipartFile file) throws IOException;
    Resource download(String filename) throws FileNotFoundException, MalformedURLException;
}
