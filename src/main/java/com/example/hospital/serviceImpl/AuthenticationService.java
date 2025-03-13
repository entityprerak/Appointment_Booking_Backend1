package com.example.hospital.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hospital.entitities.User;
import com.example.hospital.Repository.UserRegistrationRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User Authentication Logic
    public ResponseEntity<String> authenticateUser(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity<>("Email and password are required!", HttpStatus.BAD_REQUEST);
        }

        User userObj = userRegistrationRepo.findByEmail(user.getEmail());

        if (userObj == null) {
            return new ResponseEntity<>("User not found! Please register.", HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(user.getPassword(), userObj.getPassword())) {
            return new ResponseEntity<>("Invalid password!", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login successful!", HttpStatus.OK);
    }

    // User Registration Logic
    public ResponseEntity<String> registerUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return new ResponseEntity<>("Email is required!", HttpStatus.BAD_REQUEST);
        }

        if (userRegistrationRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("User already exists!", HttpStatus.CONFLICT);
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRegistrationRepo.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }
}
