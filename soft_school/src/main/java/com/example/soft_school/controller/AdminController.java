package com.example.soft_school.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String admin(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin-dashboard";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin-users-list";
    }

    @GetMapping("/admin/users/add")
    public String addUserForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin-users-add";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@RequestParam String username, @RequestParam String email, 
                         @RequestParam String password, @RequestParam String role) {
        // User creation logic would go here
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit")
    public String editUserForm(@RequestParam Long id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("userId", id);
        return "admin-users-edit";
    }

    @GetMapping("/admin/courses")
    public String listCourses(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin-courses-list";
    }

    @GetMapping("/admin/courses/add")
    public String addCourseForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin-courses-add";
    }

    @PostMapping("/admin/courses/add")
    public String addCourse(@RequestParam String title, @RequestParam String description) {
        // Course creation logic would go here
        return "redirect:/admin/courses";
    }

    @GetMapping("/admin/courses/edit")
    public String editCourseForm(@RequestParam Long id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("courseId", id);
        return "admin-courses-edit";
    }

    @GetMapping("/admin/reports")
    public String viewReports(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin-reports";
    }
}
