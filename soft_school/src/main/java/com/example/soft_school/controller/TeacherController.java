package com.example.soft_school.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeacherController {
    @GetMapping("/teacher/dashboard")
    public String teacher(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "teacher-dashboard";
    }

    @GetMapping("/teacher/courses")
    public String myCourses(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "teacher-courses";
    }

    @GetMapping("/teacher/courses/create")
    public String createCourseForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "teacher-course-create";
    }

    @PostMapping("/teacher/courses/create")
    public String createCourse(@RequestParam String title, @RequestParam String description) {
        // Course creation logic would go here
        return "redirect:/teacher/courses";
    }

    @GetMapping("/teacher/courses/edit")
    public String editCourseForm(@RequestParam Long courseId, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("courseId", courseId);
        return "teacher-course-edit";
    }

    @GetMapping("/teacher/courses/details")
    public String courseDetails(@RequestParam Long courseId, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("courseId", courseId);
        return "teacher-course-details";
    }

    @GetMapping("/teacher/students")
    public String viewStudents(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "teacher-students";
    }

    @GetMapping("/teacher/students/course")
    public String courseStudents(@RequestParam Long courseId, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("courseId", courseId);
        return "teacher-course-students";
    }

    @GetMapping("/teacher/assignments")
    public String assignments(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "teacher-assignments";
    }

    @GetMapping("/teacher/assignments/create")
    public String createAssignmentForm(@RequestParam(required = false) Long courseId, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        if (courseId != null) {
            model.addAttribute("courseId", courseId);
        }
        return "teacher-assignment-create";
    }

    @GetMapping("/teacher/assignments/grade")
    public String gradeAssignmentForm(@RequestParam Long assignmentId, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("assignmentId", assignmentId);
        return "teacher-assignment-grade";
    }

    @GetMapping("/teacher/profile")
    public String viewProfile(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "teacher-profile";
    }
}