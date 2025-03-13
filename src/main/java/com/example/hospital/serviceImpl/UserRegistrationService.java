package com.example.hospital.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.hospital.entitities.User;
import com.example.hospital.Repository.UserRegistrationRepository;
import java.util.Collections;
import java.util.List;

@Service
public class UserRegistrationService implements UserDetailsService {

	@Autowired
    private final UserRegistrationRepository userRegistrationRepo;
	@Autowired
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor Injection (No Direct Dependency on AuthenticationManager)
    public UserRegistrationService(UserRegistrationRepository userRegistrationRepo , PasswordEncoder passwordEncoder) {
        this.userRegistrationRepo = userRegistrationRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ **Spring Security - Load User by Email for Authentication**
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRegistrationRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.emptyList() // No roles/authorities assigned
        );
    }

    // ✅ **User Registration (Encrypt Password Before Storing)**
    public ResponseEntity<String> registerUser(User user) {
        if (userRegistrationRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("User already exists!", HttpStatus.CONFLICT);
        }
       // user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt Password
        userRegistrationRepo.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    // ✅ **User Authentication (Login)**
    public ResponseEntity<String> authenticateUser(User user, AuthenticationManager authenticationManager) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return new ResponseEntity<>("Login successful!", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email or password!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid credentials!", HttpStatus.UNAUTHORIZED);
    }

    // ✅ **Fetch All Users (For Admin)**
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = (List<User>) userRegistrationRepo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // ✅ **Fetch User Profile by Email**
    public ResponseEntity<?> getUserByEmail(String email) {
        User user = userRegistrationRepo.findByEmail(email);
        return (user != null)
            ? new ResponseEntity<>(user, HttpStatus.OK)
            : new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    // ✅ **Update User Profile**
    public ResponseEntity<String> updateUserProfile(User updatedUser) {
        User existingUser = userRegistrationRepo.findByEmail(updatedUser.getEmail());
        if (existingUser == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setMobile(updatedUser.getMobile());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setAddress(updatedUser.getAddress());

        // If user updates password, encrypt the new password before saving
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
          //  existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRegistrationRepo.save(existingUser);
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    // ✅ **Delete User Profile by Email**
    public ResponseEntity<String> deleteUserByEmail(String email) {
        User existingUser = userRegistrationRepo.findByEmail(email);
        if (existingUser == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        userRegistrationRepo.delete(existingUser);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }
}
