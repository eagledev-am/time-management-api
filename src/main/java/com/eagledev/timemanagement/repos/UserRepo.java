package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User , Long> {
    Optional<User> findUserByUserNameOrEmail(String username , String email);
    boolean existsUserByUserName(String username);
    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUuid(UUID uuid);
    Optional<User> findUserByUserName(String userName);
}
