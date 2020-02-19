package com.capstone.openhelp.repositories;

import com.capstone.openhelp.models.Category;
import com.capstone.openhelp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameAllIgnoreCase(String name);


}
