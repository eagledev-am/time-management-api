package com.eagledev.todoapi.services.filestore;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{


    public String uploadFile(String uploadPath , MultipartFile file) throws IOException {
        if (file == null){
            throw new BadRequestException("there is no file to upload");
        }
        Path path = Path.of(uploadPath);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        Path filePath = path.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, filePath , StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception ex){
            throw new IOException("Could not save image file: " + file.getOriginalFilename(), ex);
        }
        return file.getOriginalFilename();
    }

    @Override
    public Resource download(String fileName) throws FileNotFoundException, MalformedURLException {
        Path path = Path.of(fileName);
        if(!Files.exists(path)){
            throw new FileNotFoundException("file with this path " + fileName + "not found");
        }
        return new UrlResource(path.toUri());
    }
}
