package com.eagledev.todoapi.controlers;

import com.eagledev.todoapi.models.Response;
import com.eagledev.todoapi.models.user.ChangePasswordModel;
import com.eagledev.todoapi.models.user.UserModel;
import com.eagledev.todoapi.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers(){
        return new ResponseEntity<>(userService.getUsers() , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserModel>> getUserById(@PathVariable UUID id){
        Response<UserModel> response = new Response<>("success" , "",userService.getUserById(id) , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @GetMapping(value = "/profile/Image/{filename}"  , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getProfileImage(@PathVariable String filename){
        return new ResponseEntity<>(userService.getProfileImage(filename) , HttpStatus.OK);
    }

    @PostMapping("/upload/Image")
    public ResponseEntity<Response<String>> uploadProfileImage(@RequestParam MultipartFile file, Authentication authentication) throws IOException {
        Response<String> response = new Response<>("success" , userService.uploadProfilePicture(authentication , file) , null , null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @PostMapping("{id}/change-password")
    @PreAuthorize("#id == principal.uuid")
    public ResponseEntity<Response<String>> changePassword(@PathVariable UUID id ,@Valid @RequestBody ChangePasswordModel changePasswordModel) throws BadRequestException {
        Response<String> response = new Response<>("success" , userService.changePassword(id , changePasswordModel), null, null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == principal.uuid")
    public ResponseEntity<Response<UserModel>> deleteUser(@PathVariable UUID id){
        userService.removeUser(id);
        Response<UserModel> response = new Response<>("success" , "user account deleted successfully",null, null);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}


