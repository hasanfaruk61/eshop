package com.example.eshop.dto.cart;

import com.sun.istack.NotNull;

public class AddToCartDTO {
    private long id;
    private @NotNull long productId;
    private @NotNull int quantity;

    public AddToCartDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
