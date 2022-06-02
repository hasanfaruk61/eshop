package com.example.eshop.repository;

import com.example.eshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);

    Optional<Category> findById(Integer categoryId);
}
