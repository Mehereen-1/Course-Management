package com.example.soft_school.controller;

import com.example.soft_school.entity.Role;
import com.example.soft_school.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            Model model
    ) {
        try {
            // Remove "ROLE_" prefix if present and convert to uppercase
            String cleanRole = role.toUpperCase().replace("ROLE_", "");
            Role userRole = Role.valueOf(cleanRole);
            userService.registerUser(username, email, password, userRole);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", "Invalid role selected: " + role);
            return "signup";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "signup";
        }
    }
}
