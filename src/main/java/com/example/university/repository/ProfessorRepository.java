package com.example.university.repository;

import com.example.university.model.*;
import java.util.*;

public interface ProfessorRepository {
    ArrayList<Professor> getProfessors();

    Professor addProfessor(Professor professor);

    Professor getProfessorById(int professorId);

    Professor updateProfessor(int professorId, Professor professor);

    void deleteProfessor(int professorId);

    List<Course> getProfessorCourses(int professorId);
}