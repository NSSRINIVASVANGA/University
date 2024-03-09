package com.example.university.controller;

import com.example.university.model.*;
import com.example.university.repository.*;
import com.example.university.service.*;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class ProfessorController {
    @Autowired
    public ProfessorJpaService professorJpaService;

    @GetMapping("/professors")
    public ArrayList<Professor> getProfessors() {
        return professorJpaService.getProfessors();
    }

    @PostMapping("/professors")
    public Professor addProfessor(@RequestBody Professor professor) {
        return professorJpaService.addProfessor(professor);
    }

    @GetMapping("/professors/{professorId}")
    public Professor getProfessorById(@PathVariable("professorId") int professorId) {
        return professorJpaService.getProfessorById(professorId);
    }

    @PutMapping("/professors/{professorId}")
    public Professor updateProfessor(@PathVariable("professorId") int professorId, @RequestBody Professor professor) {
        return professorJpaService.updateProfessor(professorId, professor);
    }

    @DeleteMapping("/professors/{professorId}")
    public void deleteProfessor(@PathVariable("professorId") int professorId) {
        professorJpaService.deleteProfessor(professorId);
    }

    @GetMapping("/professors/{professorId}/courses")
    public List<Course> getProfessorCourses(@PathVariable("professorId") int professorId) {
        return professorJpaService.getProfessorCourses(professorId);
    }
}