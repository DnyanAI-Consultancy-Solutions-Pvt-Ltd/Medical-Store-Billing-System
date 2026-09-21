package com.medical.dto;

import java.util.List;

public class SignupRequest {

    private String name;
    private String username;
    private String email;
    private String mobileNumber;
    private String password;

    private List<String> allowedAgents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAllowedAgents() {
        return allowedAgents;
    }

    public void setAllowedAgents(List<String> allowedAgents) {
        this.allowedAgents = allowedAgents;
    }
}