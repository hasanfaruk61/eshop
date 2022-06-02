package com.example.eshop.controller;

import com.example.eshop.common.ApiResponse;
import com.example.eshop.dto.ProductDTO;
import com.example.eshop.model.Category;
import com.example.eshop.repository.CategoryRepository;
import com.example.eshop.service.CategoryService;
import com.example.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO productDTO) {
         Optional<Category> optionalCategory = categoryRepository.findById((productDTO.getCategoryId()));
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.BAD_REQUEST);

        }
        productService.createProduct(productDTO, optionalCategory.get());
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been added!"), HttpStatus.CREATED);

    }

    @PostMapping("/update({productId")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDTO productDTO) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById((productDTO.getCategoryId()));
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.BAD_REQUEST);

        }
        productService.updateProduct(productDTO, productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been updated!"), HttpStatus.CREATED);

    }
}
