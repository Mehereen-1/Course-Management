package com.example.soft_school.controller;

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
            userService.registerUser(username, email, password, role);
            return "redirect:/login?registered";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "signup";
        }
    }
}
