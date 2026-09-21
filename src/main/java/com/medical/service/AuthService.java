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

}