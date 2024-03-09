package com.example.university.repository;

import com.example.university.model.*;
import java.util.*;

public interface CourseRepository {
    ArrayList<Course> getCourses();

    Course addCourse(Course course);

    Course getCourseById(int courseId);

    Course updateCourse(int courseId, Course course);

    void deleteCourse(int courseId);

    List<Student> getCourseStudents(int courseId);

    Professor getCourseProfessor(int courseId);
}