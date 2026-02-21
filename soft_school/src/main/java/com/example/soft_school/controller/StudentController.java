package com.example.soft_school.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class StudentController {
    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "student-dashboard";
    }

    @GetMapping("/student/courses")
    public String myCourses(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "student-courses";
    }

    @GetMapping("/student/courses/details")
    public String courseDetails(Model model, Long courseId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("courseId", courseId);
        return "student-course-details";
    }

    @GetMapping("/student/assignments")
    public String assignments(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "student-assignments";
    }

    @GetMapping("/student/assignments/submit")
    public String submitAssignmentForm(Model model, Long assignmentId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("assignmentId", assignmentId);
        return "student-assignment-submit";
    }

    @GetMapping("/student/grades")
    public String viewGrades(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "student-grades";
    }

    @GetMapping("/student/profile")
    public String viewProfile(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "student-profile";
    }
}
