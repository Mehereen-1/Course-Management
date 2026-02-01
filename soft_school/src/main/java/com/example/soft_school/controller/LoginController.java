package com.example.soft_school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import com.example.soft_school.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model, HttpSession session) {

        com.example.soft_school.entity.User user = userRepository.findByUsername(username)
                                .orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // return login page, not index
        }

        session.setAttribute("user", user);
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        session.setAttribute("userId", user.getId());

        switch(user.getRole()) {
            case "ROLE_ADMIN": return "redirect:/admin/dashboard";
            case "ROLE_TEACHER": return "redirect:/teacher/dashboard";
            case "ROLE_STUDENT": return "redirect:/student/dashboard";
            default:
                model.addAttribute("error", "Invalid role");
                return "login";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}