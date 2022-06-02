package com.example.eshop.service;

import com.example.eshop.dto.ProductDTO;
import com.example.eshop.exceptions.ProductNotExistsException;
import com.example.eshop.model.Category;
import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void createProduct(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setDescription(productDTO.getDescription());
        product.setImageURL(productDTO.getImageURL());
        product.setName(productDTO.getName());
        product.setCategory((category));
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);

    }

    public ProductDTO getProductDTO(Product product) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription(product.getDescription());
        productDTO.setImageURL(product.getImageURL());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setId(product.getId());
        return productDTO;

    }
    public List<ProductDTO> getAllProducts() {
       List<Product> allProducts = productRepository.findAll();

        List<ProductDTO> productDTOS =new ArrayList<>();
        for (Product product: allProducts) {
            productDTOS.add(getProductDTO(product));
        }
        return productDTOS;
    }


    public void updateProduct(ProductDTO productDTO, Long productId) throws Exception {

        Optional<Product> optionalProduct = productRepository.findById((productId));
        //throw an exception if product does not exists
        if(!optionalProduct.isPresent()){
            throw new Exception("product not present");

        }

        Product product = optionalProduct.get();
        product.setDescription(productDTO.getDescription());
        product.setImageURL(productDTO.getImageURL());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);

    }
    public Product findById(Long productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.equals(null)) {
            throw new ProductNotExistsException("product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }
}
