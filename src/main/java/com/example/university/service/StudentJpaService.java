package com.example.university.service;

import com.example.university.model.*;
import com.example.university.repository.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class StudentJpaService implements StudentRepository {
    @Autowired
    public StudentJpaRepository studentJpaRepository;

    @Autowired
    public CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentsList = studentJpaRepository.findAll();
        ArrayList<Student> students = new ArrayList<>(studentsList);
        return students;
    }

    @Override
    public Student addStudent(Student student) {
        try {
            List<Integer> courseIds = new ArrayList<>();
            for (Course course : student.getCourses()) {
                courseIds.add(course.getCourseId());
            }
            List<Course> coursesList = courseJpaRepository.findAllById(courseIds);

            if (courseIds.size() != coursesList.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            student.setCourses(coursesList);

            for (Course course : coursesList) {
                course.getStudents().add(student);
            }

            Student savedStudent = studentJpaRepository.save(student);
            courseJpaRepository.saveAll(coursesList);
            return savedStudent;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        try {
            Student newStudent = studentJpaRepository.findById(studentId).get();
            if (student.getStudentName() != null) {
                newStudent.setStudentName(student.getStudentName());
            }
            if (student.getEmail() != null) {
                newStudent.setEmail(student.getEmail());
            }
            if (student.getCourses() != null) {

                List<Course> oldCoursesList = newStudent.getCourses();

                for (Course course : oldCoursesList) {
                    course.getStudents().remove(newStudent);
                }

                courseJpaRepository.saveAll(oldCoursesList);

                List<Integer> courseIds = new ArrayList<>();

                for (Course course : student.getCourses()) {
                    courseIds.add(course.getCourseId());
                }

                List<Course> newCoursesList = courseJpaRepository.findAllById(courseIds);

                if (newCoursesList.size() != courseIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Course course : newCoursesList) {
                    course.getStudents().add(newStudent);
                }

                courseJpaRepository.saveAll(newCoursesList);
                newStudent.setCourses(newCoursesList);
            }
            Student savedStudent = studentJpaRepository.save(newStudent);
            return savedStudent;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            List<Course> coursesList = student.getCourses();
            for (Course course : coursesList) {
                course.getStudents().remove(student);
            }
            courseJpaRepository.saveAll(coursesList);
            studentJpaRepository.deleteById(studentId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getStudentCourses(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            return student.getCourses();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}