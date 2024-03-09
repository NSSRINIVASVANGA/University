package com.example.university.controller;

import com.example.university.model.*;
import com.example.university.repository.*;
import com.example.university.service.*;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StudentController {
    @Autowired
    public StudentJpaService studentJpaService;

    @GetMapping("/students")
    public ArrayList<Student> getStudents() {
        return studentJpaService.getStudents();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentJpaService.addStudent(student);
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentJpaService.getStudentById(studentId);
    }

    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        return studentJpaService.updateStudent(studentId, student);
    }

    @DeleteMapping("students/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int studentId) {
        studentJpaService.deleteStudent(studentId);
    }

    @GetMapping("/students/{studentId}/courses")
    public List<Course> getStudenCourses(@PathVariable("studentId") int studentId) {
        return studentJpaService.getStudentCourses(studentId);
    }
}