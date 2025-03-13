package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.entitities.User;
import com.example.hospital.serviceImpl.UserRegistrationService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    @PostMapping("/loginuser")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        return userRegistrationService.authenticateUserAndStoreSession(user, authenticationManager, session);
    }
}
