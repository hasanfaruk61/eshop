package com.example.eshop.service;

import com.example.eshop.model.Category;
import com.example.eshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(Long categoryID, Category newCategory) {
        Category category = categoryRepository.findById(categoryID).get();
        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        category.setImageUrl(newCategory.getImageUrl());
        categoryRepository.save(category);
    }

    public boolean findById(Long categoryID) {

        return categoryRepository.findById(categoryID).isPresent();
    }


}
