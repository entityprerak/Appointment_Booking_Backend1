package com.example.hospital.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.hospital.entitities.User;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public interface UserRegistrationService extends UserDetailsService {

    ResponseEntity<?> authenticateUserAndStoreSession(User user, AuthenticationManager authenticationManager, HttpSession session);
    
    ResponseEntity<?> getUserByEmail(String email);
    
    ResponseEntity<String> registerUser(User user);
    
    ResponseEntity<List<User>> getAllUsers();
    
    ResponseEntity<?> getUserByEmailProfile(String email);
    
    ResponseEntity<String> updateUserProfile(User updatedUser);
    
    ResponseEntity<String> deleteUserByEmail(String email);
    
    ResponseEntity<?> getUserIdFromSession(HttpSession session);
}
