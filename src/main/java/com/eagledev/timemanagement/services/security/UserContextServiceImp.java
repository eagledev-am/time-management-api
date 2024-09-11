package com.eagledev.timemanagement.services.security;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.exceptions.UnAuthorizedException;
import com.eagledev.timemanagement.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserContextServiceImp implements UserContextService {
    private final UserRepo userRepo;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new UnAuthorizedException("No authenticated user");
        }

        return (User)(authentication.getPrincipal());
    }
}
