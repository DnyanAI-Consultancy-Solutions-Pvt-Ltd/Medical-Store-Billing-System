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

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
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

    	    if (!user.getAgent().equals(request.getAgent())) {
    	        return ResponseEntity
    	                .status(HttpStatus.UNAUTHORIZED)
    	                .body("You are not registered for this Agent. Please select the Agent you selected during Signup.");
    	    }
        if(authService.login(
                request.getUsername(),
                request.getPassword())){

            return "LOGIN_SUCCESS";

        }

        return "INVALID_CREDENTIALS";

    }
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody SignupRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {

            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }

        User user = new User();

        user.setFullName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(request.getPassword());

        // Convert List to String
        user.setAllowedAgents(
                String.join(",", request.getAllowedAgents())
        );

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

}