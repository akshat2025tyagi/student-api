
package com.example.student_api.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private int studentId;

    private String studentName;

    @Column(unique = true)
    private String email;

    private String courseName;

    private double programmingScore;

    private double mathematicsScore;

    @ElementCollection
    @CollectionTable(name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> technicalSkills;

    public Student() {
    }

    public Student(int studentId, String studentName, String email,
                  String courseName, double programmingScore,
                  double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public double getAverage() {
        return (programmingScore + mathematicsScore) / 2;
    }

    public String getCategory() {
        double avg = getAverage();

        if (avg >= 85) return "Excellent";
        if (avg >= 70) return "Good";
        if (avg >= 50) return "Satisfactory";
        return "Needs Improvement";
    }

    public String getStrongerSubject() {
        if (programmingScore > mathematicsScore)
            return "Programming";
        if (mathematicsScore > programmingScore)
            return "Mathematics";
        return "Equal";
    }

    @Override
    public String toString() {
        return "ID: " + studentId +
                ", Name: " + studentName +
                ", Email: " + email +
                ", Average: " + getAverage() +
                ", Category: " + getCategory() +
                ", Stronger Subject: " + getStrongerSubject() +
                ", Skills: " + technicalSkills;
    }
}
