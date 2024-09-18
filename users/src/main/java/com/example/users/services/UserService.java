package com.example.users.services;

import com.example.users.controller.UserController;
import com.example.users.model.User;
import com.example.users.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public String verifyUser(User user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPwd()));
        if (authentication.isAuthenticated()) {
            logger.info("Generating tokens for user ");
            return jwtService.generateToken(authentication.getName());
        }
        logger.error("Failed to generate token for user. Email :"+user.getEmail());
        return "Failed to generate token for user. Email :"+user.getEmail();
    }

    public List<User> getAllUsers() {
        logger.info("Fetching list of all users");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public String saveUser(User user) {

        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 == null) {
            logger.info("Registering new user to Database ");
            userRepository.save(user);
            return "User is Registered Successfully.";
        }
        logger.info("User found with the Email :"+user.getEmail());
        return "User found with the Email :"+user.getEmail();
    }

    public Boolean updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setName(userDetails.getName());
            updatedUser.setEmail(userDetails.getEmail());
            return true;
        } else {
            return false;
        }
    }

    public String validateUser(User user) {
        if(user.getName().isEmpty()){
            return "Name is required";
        }
        if(user.getEmail().isEmpty()){
            return "Email is required";
        } else if (!(user.getEmail().contains("@") && user.getEmail().contains(".com"))) {
            return "Invalid email";
        }
        if(user.getPwd().isEmpty() || user.getPwd().length() < 8 ){
            return "Password is required and length should be greater then 8";
        }
        return "";
    }
}
