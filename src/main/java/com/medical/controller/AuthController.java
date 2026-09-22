package com.medical.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medical.dto.LoginRequest;
import com.medical.dto.SignupRequest;
import com.medical.entity.User;
import com.medical.repository.UserRepository;
import com.medical.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<User> userOptional =
                userRepository.findByUsername(request.getUsername());

        // Username check
        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        User user = userOptional.get();

        // Password check
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        // Login successful
        return ResponseEntity.ok("LOGIN_SUCCESS");
    }

    // =========================
    // LOGIN
    // =========================

    @PostMapping("/dnyanailogin")
    public ResponseEntity<String> dnyanailogin(
            @RequestBody LoginRequest request) {

        String result = authService.dnyanailogin(
                request.getUsername(),
                request.getPassword(),
                request.getAgent()
        );

        switch (result) {

            case "LOGIN_SUCCESS":
                return ResponseEntity.ok("LOGIN_SUCCESS");

            case "USERNAME_NOT_FOUND":
                return ResponseEntity
                        .badRequest()
                        .body("Username not found. Please check your username.");

            case "INVALID_PASSWORD":
                return ResponseEntity
                        .badRequest()
                        .body("Incorrect password. Please enter the correct password.");

            case "AGENT_NOT_ALLOWED":
                return ResponseEntity
                        .badRequest()
                        .body("You are not registered for this Agent. Please login using the Agent selected during Signup.");

            case "NO_AGENT_ASSIGNED":
                return ResponseEntity
                        .badRequest()
                        .body("No Agent is assigned to this account. Please contact the administrator.");

            default:
                return ResponseEntity
                        .badRequest()
                        .body("Login failed. Please try again.");
        }
    }

    // =========================
    // SIGNUP
    // =========================

    @PostMapping("/dnyanaisignup")
    public ResponseEntity<?> signup(
            @RequestBody SignupRequest request) {

        // Username already exists
        if (userRepository.existsByUsername(
                request.getUsername())) {

            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }

        // Agent validation
        if (request.getAllowedAgents() == null
                || request.getAllowedAgents().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please select at least one Agent.");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(request.getPassword());

        // Save selected agents as comma-separated string
        user.setAllowedAgents(
                String.join(",", request.getAllowedAgents())
        );

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }
   
}