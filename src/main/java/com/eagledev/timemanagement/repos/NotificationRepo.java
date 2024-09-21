package com.eagledev.timemanagement.repos;


import com.eagledev.timemanagement.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    Page<Notification> findAllByUserId(int userId , Pageable pageable);
    boolean existsByIdAndUserId(int id , int userId);
}
