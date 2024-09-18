package com.eagledev.timemanagement.services.user;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.models.user.ChangePasswordModel;
import com.eagledev.timemanagement.models.user.UserModel;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserModel getUserForClientById(UUID id);
    User getUserById(UUID id);
    User getUserByUsername(String username);
    List<UserModel> getUsers();
    void removeUser(UUID id);
    String uploadProfilePicture(Authentication authentication, MultipartFile file) throws IOException;
    Resource getProfileImage(String fileName);
    String changePassword(UUID id , ChangePasswordModel changePasswordModel) throws BadRequestException;
}
