package com.eagledev.timemanagement.services.listcategory;

import com.eagledev.timemanagement.entities.User;
import com.eagledev.timemanagement.repos.ListCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListCategoryAuthorization {
    private final ListCategoryRepo repo;

    public boolean isListOwner(Authentication authentication , int listId){
        User user = (User)authentication.getPrincipal();
        return repo.existsByIdAndUser(listId , user);
    }
}
