package com.example.university.service;

import com.example.university.model.*;
import com.example.university.repository.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class CourseJpaService implements CourseRepository {
    @Autowired
    public CourseJpaRepository courseJpaRepository;

    @Autowired
    public StudentJpaRepository studentJpaRepository;

    @Autowired
    public ProfessorJpaRepository professorJpaRepository;

    @Override
    public ArrayList<Course> getCourses() {
        List<Course> coursesList = courseJpaRepository.findAll();
        ArrayList<Course> courses = new ArrayList<>(coursesList);
        return courses;
    }

    @Override
    public Course getCourseById(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course addCourse(Course course) {
        try {
            Professor professor = course.getProfessor();
            int id = professor.getProfessorId();
            Professor newProfessor = professorJpaRepository.findById(id).get();
            course.setProfessor(newProfessor);

            List<Integer> studentIds = new ArrayList<>();
            for (Student student : course.getStudents()) {
                studentIds.add(student.getStudentId());
            }

            List<Student> studentsList = studentJpaRepository.findAllById(studentIds);

            if (studentIds.size() != studentsList.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some Students are not found...");
            }

            course.setStudents(studentsList);
            Course savedCourse = courseJpaRepository.save(course);
            return savedCourse;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
        }
    }

    @Override
    public Course updateCourse(int courseId, Course course) {
        try {
            Course newCourse = courseJpaRepository.findById(courseId).get();
            if (course.getCourseName() != null) {
                newCourse.setCourseName(course.getCourseName());
            }
            if (course.getCredits() != 0) {
                newCourse.setCredits(course.getCredits());
            }
            if (course.getProfessor() != null) {
                Professor professor = course.getProfessor();
                int professorId = professor.getProfessorId();
                Professor newProfessor = professorJpaRepository.findById(professorId).get();
                newCourse.setProfessor(newProfessor);
            }
            if (course.getStudents() != null) {
                List<Integer> studentIds = new ArrayList<>();

                for (Student student : course.getStudents()) {
                    studentIds.add(student.getStudentId());
                }

                List<Student> newStudentsList = studentJpaRepository.findAllById(studentIds);

                if (newStudentsList.size() != studentIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some Students are not found...");
                }

                newCourse.setStudents(newStudentsList);
            }
            Course savedCourse = courseJpaRepository.save(newCourse);
            return savedCourse;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
        }
    }

    @Override
    public void deleteCourse(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            List<Student> students = course.getStudents();
            for (Student student : students) {
                student.getCourses().remove(course);
            }
            studentJpaRepository.saveAll(students);
            courseJpaRepository.deleteById(courseId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Student> getCourseStudents(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getStudents();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor getCourseProfessor(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getProfessor();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}