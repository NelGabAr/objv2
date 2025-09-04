package com.example.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public User save(User user){
        return userRepo.save(user);
    }

    public User newUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        System.err.println("para guardar");
        return userRepo.save(user);
    }

    public boolean correctPass(String pass, User user){

        return passwordEncoder.matches(pass, user.getPassword());
    }

    public User getUserByUsername(String username){

        return userRepo.findByUsername(username);
    }

}
