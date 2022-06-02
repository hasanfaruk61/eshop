package com.example.eshop.service;

import com.example.eshop.dto.ResponseDTO;
import com.example.eshop.dto.user.SignInDTO;
import com.example.eshop.dto.user.SignInResponseDTO;
import com.example.eshop.dto.user.SignUpDTO;

import com.example.eshop.model.Role;
import com.example.eshop.exceptions.AuthenticationFailException;
import com.example.eshop.exceptions.CustomException;
import com.example.eshop.model.AuthenticationToken;
import com.example.eshop.model.User;
import com.example.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    public ResponseDTO signUp(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {
        //check if user already present
        if(Objects.nonNull(userRepository.findByEmail(signUpDTO.getEmail()))){

            //we have an user
            throw new CustomException("User already present");
        }

        //hash the password
        String encryptedpassword = signUpDTO.getPassword();
        try{
            encryptedpassword = hashPassword(signUpDTO.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //save user
        User user = new User(signUpDTO.getFirstName(), signUpDTO.getLastName(), signUpDTO.getEmail(), encryptedpassword);
        user.setRole(Role.USER);
        userRepository.save(user);


        //create token
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
        ResponseDTO responseDTO = new ResponseDTO("success", "user created successfully");
        return responseDTO;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(("MD5"));
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDTO signIn(SignInDTO signInDTO) {
        //find user by email
        User user = userRepository.findByEmail((signInDTO.getEmail()));
        if(Objects.isNull(user)){

                throw new AuthenticationFailException("user is not valid");

        }
        //hash the password
        //compare the password in DB
        try {
            if(!user.getPassword().equals(hashPassword(signInDTO.getPassword()))){
                throw  new AuthenticationFailException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //if password match
        AuthenticationToken token = authenticationService.getToken(user);
        //retrieve token
        if(Objects.isNull(token)){
            throw new CustomException("token is not present");

        }
        return  new SignInResponseDTO("success", token.getToken());
    }
}
