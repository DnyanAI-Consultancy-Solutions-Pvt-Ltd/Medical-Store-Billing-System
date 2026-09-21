package com.medical.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entity.User;
import com.medical.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    public boolean login(String username,String password){

        Optional<User> user =
                repository.findByUsername(username);

        if(user.isPresent()){

            return user.get()
                    .getPassword()
                    .equals(password);

        }

        return false;

    }
    public String dnyanailogin(String username, String password, String agent) {

        Optional<User> userOptional =
                repository.findByUsername(username);

        // Username does not exist
        if (userOptional.isEmpty()) {
            return "USERNAME_NOT_FOUND";
        }

        User user = userOptional.get();

        // Password incorrect
        if (!user.getPassword().equals(password)) {
            return "INVALID_PASSWORD";
        }

        // Agent validation
        String allowedAgents = user.getAllowedAgents();

        if (allowedAgents == null || allowedAgents.trim().isEmpty()) {
            return "NO_AGENT_ASSIGNED";
        }

        if (agent == null || agent.trim().isEmpty()) {
            return "AGENT_NOT_ALLOWED";
        }

        boolean agentAllowed = false;

        String[] agents = allowedAgents.split(",");

        for (String allowedAgent : agents) {

            if (allowedAgent.trim()
                    .equalsIgnoreCase(agent.trim())) {

                agentAllowed = true;
                break;
            }
        }

        if (!agentAllowed) {
            return "AGENT_NOT_ALLOWED";
        }

        return "LOGIN_SUCCESS";
    }
}