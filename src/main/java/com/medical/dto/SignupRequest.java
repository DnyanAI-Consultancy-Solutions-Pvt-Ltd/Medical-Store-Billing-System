package com.medical.dto;
 
import java.util.List;
 
public class SignupRequest {
 
    private String fullName;
 
    private String username;
 
    private String email;
 
    private String mobileNumber;
 
    private String password;
 
    private List<String> allowedAgents;
 
    // =========================
    // FULL NAME
    // =========================
 
    public String getFullName() {
        return fullName;
    }
 
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
 
    // =========================
    // USERNAME
    // =========================
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    // =========================
    // EMAIL
    // =========================
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    // =========================
    // MOBILE NUMBER
    // =========================
 
    public String getMobileNumber() {
        return mobileNumber;
    }
 
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
 
    // =========================
    // PASSWORD
    // =========================
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    // =========================
    // ALLOWED AGENTS
    // =========================
 
    public List<String> getAllowedAgents() {
        return allowedAgents;
    }
 
    public void setAllowedAgents(List<String> allowedAgents) {
        this.allowedAgents = allowedAgents;
    }
}