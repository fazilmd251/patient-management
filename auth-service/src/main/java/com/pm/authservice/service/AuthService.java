package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    //constructor
    public AuthService(UserService userService){
        this.userService=userService;
    }
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
     Optional<String> token=userService.findByEmail(loginRequestDTO.getEmail())
             .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
             .map(u->jwtUtil.generateToken(u.getEmail(),u.getRole()));
     return token;
    }
}
