package com.example.hospital.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.entitities.User;
import com.example.hospital.service.UserRegistrationService;
import com.example.hospital.serviceImpl.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getUserProfile(@PathVariable String email) {
        return userRegistrationService.getUserByEmail(email);
    }

    @PutMapping("/updateuser")
    public ResponseEntity<?> updateUserProfile(@RequestBody User user) {
        return userRegistrationService.updateUserProfile(user);
    }

    @DeleteMapping("/deleteuser/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        return userRegistrationService.deleteUserByEmail(email);
    }
}
