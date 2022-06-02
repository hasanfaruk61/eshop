package com.example.eshop.service;

import com.example.eshop.model.User;
import com.example.eshop.repository.UserRepository;
import com.example.eshop.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username);
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for(String role : Role){
//
//        }
//
//        return null;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
//        return null;
//    }


        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("Not found: " + email));

           Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.get().getRole().name()));
            return null;
        }
}
