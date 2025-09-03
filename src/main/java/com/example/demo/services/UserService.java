package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }


    public User save(User user){
        return userRepo.save(user);
    }

    //in the shor future this should store the hashed pass
    public User newUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepo.save(user);
    }
}
