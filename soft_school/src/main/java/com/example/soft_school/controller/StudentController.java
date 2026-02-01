package com.example.soft_school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class StudentController {
    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model, HttpSession session) {
        // Check if user is logged in
        Object username = session.getAttribute("username");
        Object role = session.getAttribute("role");
        
        if (username == null || !"ROLE_STUDENT".equals(role)) {
            return "redirect:/login";
        }
        
        // Add user info to model
        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("userId", session.getAttribute("userId"));
        
        return "student-dashboard"; // Your HTML file name
    }

}
