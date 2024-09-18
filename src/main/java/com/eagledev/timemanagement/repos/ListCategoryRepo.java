package com.eagledev.timemanagement.repos;

import com.eagledev.timemanagement.entities.ListCategory;
import com.eagledev.timemanagement.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListCategoryRepo extends JpaRepository<ListCategory, Integer> {
    Page<ListCategory> findAllByUser(User user, Pageable pageable);
    boolean existsByIdAndUser(int listId , User user);
}
