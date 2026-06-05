package com.medical.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request){

        if("admin".equals(request.getUsername())
                &&
           "admin123".equals(request.getPassword())){

            return "LOGIN_SUCCESS";
        }

        return "INVALID_CREDENTIALS";
    }
}