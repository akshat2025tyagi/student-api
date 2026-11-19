package com.example.student_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_api.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);

    List<Student> findByCourseName(String courseName);
}