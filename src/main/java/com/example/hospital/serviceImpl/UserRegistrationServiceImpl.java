package com.example.hospital.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hospital.entitities.User;
import com.example.hospital.Repository.UserRegistrationRepository;
import com.example.hospital.service.UserRegistrationService;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRegistrationRepository userRegistrationRepo;
    private final PasswordEncoder passwordEncoder;
  //  private final AuthenticationManager authenticationManager;  // ✅ Only if authentication is required

    public UserRegistrationServiceImpl(UserRegistrationRepository userRegistrationRepo, PasswordEncoder passwordEncoder){//, AuthenticationManager authenticationManager) {
        this.userRegistrationRepo = userRegistrationRepo;
        this.passwordEncoder = passwordEncoder;
    //    this.authenticationManager = authenticationManager; // ✅ Injected only if needed
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
            Collections.emptyList()
        );
    }

    // ✅ **Authenticate User & Store User ID in Session**
    @Override
    public ResponseEntity<?> authenticateUserAndStoreSession(User user, AuthenticationManager authenticationManager, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                User loggedInUser = userRegistrationRepo.findByEmail(user.getEmail());
                if (loggedInUser != null) {
                    session.setAttribute("userId", loggedInUser.getId());

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("message", "Login successful!");
                    responseBody.put("userId", loggedInUser.getId());

                    return ResponseEntity.ok(responseBody);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email or password!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid credentials!", HttpStatus.UNAUTHORIZED);
    }

    // ✅ **Fetch User by Email**
    @Override
    public ResponseEntity<?> getUserByEmail(String email) {
        User user = userRegistrationRepo.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
    }

    // ✅ **User Registration**
    @Override
    public ResponseEntity<String> registerUser(User user) {
        if (userRegistrationRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("User already exists!", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt Password
        userRegistrationRepo.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    // ✅ **Fetch All Users**
    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = (List<User>) userRegistrationRepo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // ✅ **Fetch User Profile by Email**
    @Override
    public ResponseEntity<?> getUserByEmailProfile(String email) {
        User user = userRegistrationRepo.findByEmail(email);
        return (user != null)
            ? new ResponseEntity<>(user, HttpStatus.OK)
            : new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    // ✅ **Update User Profile**
    @Override
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

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRegistrationRepo.save(existingUser);
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    // ✅ **Delete User Profile**
    @Override
    public ResponseEntity<String> deleteUserByEmail(String email) {
        User existingUser = userRegistrationRepo.findByEmail(email);
        if (existingUser == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        userRegistrationRepo.delete(existingUser);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

    // ✅ **Retrieve User ID from Session**
    @Override
    public ResponseEntity<?> getUserIdFromSession(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
        }
    }
}
