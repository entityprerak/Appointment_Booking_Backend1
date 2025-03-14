package com.example.hospital.service;

import org.springframework.http.ResponseEntity;
import com.example.hospital.entitities.User;

public interface AuthenticationService {
    
    ResponseEntity<String> authenticateUser(User user);
    
    ResponseEntity<String> registerUser(User user);
}
