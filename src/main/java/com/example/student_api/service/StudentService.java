package com.example.student_api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.student_api.entity.Student;
import com.example.student_api.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student addStudent(Student s) {
        validateScores(s);

        if (repo.existsById(s.getStudentId()))
            throw new IllegalArgumentException("Duplicate Student ID");

        if (repo.findByEmail(s.getEmail()) != null)
            throw new IllegalArgumentException("Duplicate Email");

        return repo.save(s);
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Student getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public List<Student> getByCourse(String course) {
        return repo.findByCourseName(course);
    }

    public List<Student> meritList() {
        List<Student> list = repo.findAll();

        list.sort((a, b) ->
                Double.compare(b.getAverage(), a.getAverage()));

        return list;
    }

    public Student updateStudent(int id, Student updated) {
        Student s = repo.findById(id).orElse(null);

        if (s == null) return null;

        validateScores(updated);

        s.setProgrammingScore(updated.getProgrammingScore());
        s.setMathematicsScore(updated.getMathematicsScore());
        s.setTechnicalSkills(updated.getTechnicalSkills());

        return repo.save(s);
    }

    public boolean deleteStudent(int id) {
        if (!repo.existsById(id)) return false;

        repo.deleteById(id);
        return true;
    }

    public List<Student> programmingImprovement() {
        List<Student> result = new ArrayList<>();

        for (Student s : repo.findAll()) {
            if (s.getProgrammingScore() < 70
                    && s.getMathematicsScore() >= 75) {
                result.add(s);
            }
        }

        return result;
    }

    public Set<String> sharedSkills() {
        Map<String, Integer> count = new HashMap<>();

        for (Student s : repo.findAll()) {
            if (s.getTechnicalSkills() != null) {
                for (String skill : new HashSet<>(s.getTechnicalSkills())) {
                    count.put(skill, count.getOrDefault(skill, 0) + 1);
                }
            }
        }

        Set<String> shared = new HashSet<>();

        for (String skill : count.keySet()) {
            if (count.get(skill) > 1)
                shared.add(skill);
        }

        return shared;
    }

    private void validateScores(Student s) {
        if (s.getProgrammingScore() < 0
                || s.getProgrammingScore() > 100
                || s.getMathematicsScore() < 0
                || s.getMathematicsScore() > 100) {
            throw new IllegalArgumentException(
                    "Scores must be between 0 and 100");
        }
    }
}