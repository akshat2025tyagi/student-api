package com.example.student_api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_api.entity.Student;
import com.example.student_api.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student s) {
        try {
            return ResponseEntity.ok(service.addStudent(s));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Student s = service.getById(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s);
    }

    @GetMapping("/search/email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        Student s = service.getByEmail(email);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s);
    }

    @GetMapping("/search/course")
    public List<Student> getByCourse(@RequestParam String course) {
        return service.getByCourse(course);
    }

    @GetMapping("/merit")
    public List<Student> meritList() {
        return service.meritList();
    }

    @GetMapping("/shared-skills")
    public Set<String> sharedSkills() {
        return service.sharedSkills();
    }

    @GetMapping("/programming-improvement")
    public List<Student> programmingImprovement() {
        return service.programmingImprovement();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable int id, @RequestBody Student updated) {
        try {
            Student s = service.updateStudent(id, updated);
            if (s == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(s);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        if (!service.deleteStudent(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok("Student deleted successfully");
    }
}