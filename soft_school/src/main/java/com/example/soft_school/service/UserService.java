package com.example.soft_school.service;

import com.example.soft_school.entity.User;
import com.example.soft_school.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String email,
                             String password, String role) {

        // prevent duplicate usernames
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_" + role);

        userRepository.save(user);
    }
}
