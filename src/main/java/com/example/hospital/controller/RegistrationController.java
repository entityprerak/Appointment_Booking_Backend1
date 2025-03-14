package com.example.hospital.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.hospital.entitities.User;
import com.example.hospital.service.AuthenticationService;
import com.example.hospital.serviceImpl.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registeruser")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return authenticationService.registerUser(user);
    }
}

