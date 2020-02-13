package com.capstone.openhelp.repositories;

import com.capstone.openhelp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category, Long> {
}
