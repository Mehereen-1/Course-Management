package com.example.soft_school.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private Set<Teacher> teachers;

    // getters & setters
}
