package com.example.eshop.controller;

import com.example.eshop.common.ApiResponse;
import com.example.eshop.dto.cart.AddToCartDTO;
import com.example.eshop.dto.cart.CartDTO;
import com.example.eshop.model.User;
import com.example.eshop.service.AuthenticationService;
import com.example.eshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    //post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDTO addToCartDTO,
                                                 @RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);
        cartService.addToCart(addToCartDTO, user );
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    // get all cart items for a user
    @GetMapping("/")
    public ResponseEntity<CartDTO> getCartItems(@RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        // get cart items

        CartDTO cartDTO = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    // delete a cart item for a user

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
                                                      @RequestParam("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);

    }

}
