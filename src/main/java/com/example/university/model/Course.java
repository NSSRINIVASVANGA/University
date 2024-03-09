package com.example.university.model;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int courseId;
    @Column(name = "name")
    private String courseName;
    @Column(name = "credits")
    private int credits;

    @ManyToOne
    @JoinColumn(name = "professorid")
    private Professor professor;

    @ManyToMany
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "courseid"), inverseJoinColumns = @JoinColumn(name = "studentid"))
    @JsonIgnoreProperties("courses")
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(int courseId, String courseName, int credits, Professor professor, List<Student> students) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.professor = professor;
        this.students = students;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public Professor getProfessor() {
        return professor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}