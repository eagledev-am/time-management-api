package com.eagledev.todoapi.repos;

import com.eagledev.todoapi.entities.ListCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListCategoryRepo extends JpaRepository<ListCategory, Long> {
}
