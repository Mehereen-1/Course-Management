package com.example.soft_school.controller;

import com.example.soft_school.entity.Course;
import com.example.soft_school.entity.Role;
import com.example.soft_school.entity.User;
import com.example.soft_school.repository.CourseRepository;
import com.example.soft_school.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request,
                                               Authentication authentication) {
        User teacher = getCurrentUser(authentication);
        Course course = new Course();
        course.setTitle(request.title());
        course.setOwner(teacher);
        course.setEnrolledStudents(new HashSet<>());
        return ResponseEntity.ok(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                               @RequestBody CourseRequest request,
                                               Authentication authentication) {
        User teacher = getCurrentUser(authentication);
        Course course = courseRepository.findById(id).orElseThrow();
        if (!course.getOwner().getId().equals(teacher.getId())) {
            return ResponseEntity.status(403).build();
        }
        course.setTitle(request.title());
        return ResponseEntity.ok(courseRepository.save(course));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id,
                                             Authentication authentication) {
        User user = getCurrentUser(authentication);
        Course course = courseRepository.findById(id).orElseThrow();

        if (user.getRole() == Role.TEACHER && !course.getOwner().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        courseRepository.delete(course);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Course> enroll(@PathVariable Long id, Authentication authentication) {
        User student = getCurrentUser(authentication);
        Course course = courseRepository.findById(id).orElseThrow();
        if (course.getEnrolledStudents() == null) {
            course.setEnrolledStudents(new HashSet<>());
        }
        course.getEnrolledStudents().add(student);
        return ResponseEntity.ok(courseRepository.save(course));
    }

    private User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public record CourseRequest(String title) {}
}
