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

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Username does not exist");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }

        // Check Agent Access
        if (user.getAllowedAgents() == null ||
            !user.getAllowedAgents().contains(request.getAgent())) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("You are not registered for this Agent. Please select the Agent you selected during Signup.");
        }

        if (authService.login(
                request.getUsername(),
                request.getPassword())) {

            return ResponseEntity.ok("LOGIN_SUCCESS");
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("INVALID_CREDENTIALS");
    }

   
}