package com.example.eshop.service;

import com.example.eshop.dto.cart.AddToCartDTO;
import com.example.eshop.dto.cart.CartDTO;
import com.example.eshop.dto.cart.CartItemDTO;
import com.example.eshop.exceptions.CustomException;
import com.example.eshop.model.Cart;
import com.example.eshop.model.Product;
import com.example.eshop.model.User;
import com.example.eshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDTO addToCartDTO, User user) {

        // validate if the product id is valid
        Product product = productService.findById(addToCartDTO.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setCreatedDate(new Date());
        // save the cart
        cartRepository.save(cart);

    }

    public CartDTO listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDTO> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDTO cartItemDto = new CartItemDTO(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalCost(totalCost);
        cartDTO.setCartItems(cartItems);
        return  cartDTO;
    }

    public void deleteCartItem(long cartItemId, User user) {
        // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.equals(null)) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw  new CustomException("cart item does not belong to user: " +cartItemId);
        }

        cartRepository.delete(cart);


    }
}
