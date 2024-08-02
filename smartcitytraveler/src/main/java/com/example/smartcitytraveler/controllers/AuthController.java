package com.example.smartcitytraveler.controllers;

import com.example.smartcitytraveler.entities.User;
import com.example.smartcitytraveler.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        logger.info("Register request received for username: {}", user.getUsername());
        if (userService.existsByUsername(user.getUsername())) {
            logger.error("Username {} already exists!", user.getUsername());
            throw new RuntimeException("Username already exists!");
        }
        User savedUser = userService.saveUser(user);
        logger.info("User {} registered successfully", savedUser.getUsername());
        return savedUser;
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        logger.info("Login request received for username: {}", user.getUsername());
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser == null || !user.getPassword().equals(existingUser.getPassword())) {
            logger.error("Invalid username or password for user {}", user.getUsername());
            throw new RuntimeException("Invalid username or password!");
        }
        logger.info("User {} logged in successfully", user.getUsername());
        return existingUser;
    }
}
