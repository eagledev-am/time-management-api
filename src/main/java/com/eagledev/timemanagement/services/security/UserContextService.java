package com.eagledev.timemanagement.services.security;

import com.eagledev.timemanagement.entities.User;

public interface UserContextService {
    User getCurrentUser();
}
