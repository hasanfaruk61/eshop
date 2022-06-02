package com.example.eshop.controller;

import com.example.eshop.common.ApiResponse;
import com.example.eshop.model.Category;
import com.example.eshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "A new Category is created"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<Category> listCategory() {

        return categoryService.listCategories();
    }

    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryID, @RequestBody Category category ) {
        System.out.println("category id " + categoryID);
        if (!categoryService.findById(categoryID)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
        }
        categoryService.updateCategory(categoryID, category );
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "category has been updated"), HttpStatus.OK);
    }


}
