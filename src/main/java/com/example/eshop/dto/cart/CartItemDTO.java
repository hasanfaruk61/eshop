package com.example.eshop.dto.cart;

import com.example.eshop.model.Cart;
import com.example.eshop.model.Product;

public class CartItemDTO {

    private Long id;
    private Integer quantity;
    private Product product;

    public CartItemDTO() {

    }

    public CartItemDTO(Long id, Integer quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItemDTO(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}
