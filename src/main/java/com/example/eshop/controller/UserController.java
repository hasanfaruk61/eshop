package com.example.eshop.controller;

import com.example.eshop.dto.ResponseDTO;
import com.example.eshop.dto.user.SignInDTO;
import com.example.eshop.dto.user.SignInResponseDTO;
import com.example.eshop.dto.user.SignUpDTO;
import com.example.eshop.exceptions.CustomException;
import com.example.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;
    //apis
    //signup
    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody SignUpDTO signUpDTO) throws CustomException, NoSuchAlgorithmException {
        return userService.signUp(signUpDTO);
    }

    //signin
    @PostMapping("/signin")
    public SignInResponseDTO signIn(@RequestBody SignInDTO signInDTO) throws CustomException{
        return userService.signIn(signInDTO);
    }
}
