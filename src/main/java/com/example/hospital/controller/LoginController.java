package com.example.hospital.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.User;
import com.example.hospital.service.UserRegistrationService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @PostMapping("/loginuser")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        // ✅ Admin Login (Hardcoded)
        if ("admin@example.com".equals(user.getEmail()) && "admin123".equals(user.getPassword())) {
            session.setAttribute("admin", true);
            return ResponseEntity.ok(Map.of("message", "Admin Login Successful!", "role", "ROLE_ADMIN"));
        }

        // ✅ Normal User Login
        return userRegistrationService.authenticateUserAndStoreSession(user, authenticationManager, session);
    }
    
    @PostMapping("/logindoctor")
    public ResponseEntity<?> loginDoctor(@RequestBody Doctor doctor) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(doctor.getEmail());

        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found. Please register.");
        }

        Doctor existingDoctor = optionalDoctor.get();

        // ✅ Use BCrypt if passwords are hashed
        if (!passwordEncoder.matches(doctor.getPassword(), existingDoctor.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        if (!"APPROVED".equals(existingDoctor.getStatus())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your registration is pending approval.");
        }

        // ✅ Send doctorId in response (Frontend should store it)
        return ResponseEntity.ok(Map.of("message", "Login successful!", "role", "ROLE_DOCTOR", "doctorId", existingDoctor.getId()));
    }}
