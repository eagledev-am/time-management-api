package com.eagledev.timemanagement.services.user;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.exceptions.UserNotFoundException;
import com.eagledev.timemanagement.models.user.ChangePasswordModel;
import com.eagledev.timemanagement.models.user.UserModel;
import com.eagledev.timemanagement.repos.UserRepo;
import com.eagledev.timemanagement.services.filestore.FileService;
import com.eagledev.timemanagement.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FileService fileService;

    @Override
    public UserModel getUserForClientById(UUID id) {
        User user = findUserById(id);
        return userMapper.toModel(user);
    }

    @Override
    public User getUserById(UUID id) {
        return findUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findUserByUserName(username)
                .orElseThrow(() ->  new UserNotFoundException("User not found !" , HttpStatus.NOT_FOUND.value()));
    }


    @Override
    public List<UserModel> getUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toModel)
                .toList();
    }

    @Override
    public void removeUser(UUID uuid) {
        User user = findUserById(uuid);
        userRepo.delete(user);
    }


    @Override
    public String uploadProfilePicture(Authentication authentication, MultipartFile file) throws IOException {
        User user = findUserByUserName(authentication.getName());
        String contentType = file.getContentType();
        System.out.println(contentType);
        if(contentType == null || !contentType.equals(MediaType.IMAGE_JPEG_VALUE) && !contentType.equals(MediaType.IMAGE_PNG_VALUE)) {
            throw new UnsupportedMediaTypeStatusException("UnSupportedMediaType");
        }
        String imageName = fileService.uploadFile("uploads/Image/" , file);
        String imagePath = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("api/users/profile/Image/" + imageName)
                .toUriString();
        user.setProfilePictureUrl(imagePath);
        userRepo.save(user);
        return "Image Uploaded successfully !";
    }

    @SneakyThrows
    @Override
    public Resource getProfileImage(String fileName) {
        String filePath = "uploads/Image/" + fileName;
        return fileService.loadFile(filePath);
    }

//    @SneakyThrows
    @Override
    public String changePassword(UUID id , ChangePasswordModel changePasswordModel) throws BadRequestException {
        User user = findUserById(id);
        if(!passwordEncoder.matches(changePasswordModel.getOldPassword() , user.getPassword())){
            throw new AuthenticationException("Old password is incorrect !") {
            };
        }

        if(!changePasswordModel.getConfirmPassword().equals(changePasswordModel.getNewPassword())){
            throw new BadRequestException("Confirm password does not match !");
        }

        if(passwordEncoder.matches(changePasswordModel.getNewPassword() , user.getPassword())){
            throw new BadRequestException("Old password can not be the same !");
        }
        System.out.println("Password changed successfully !");
        user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
        userRepo.save(user);
        return "user password updated successfully !";
    }

    private User findUserById(UUID id){
        return userRepo.findUserByUuid(id).orElseThrow(
                () ->  new UserNotFoundException("User not found !" , HttpStatus.NOT_FOUND.value()));
    }

    private User findUserByUserName(String name) {
        return userRepo.findUserByUserName(name).orElseThrow(
                () ->  new UserNotFoundException("User not found !" , HttpStatus.NOT_FOUND.value()));
    }
}
