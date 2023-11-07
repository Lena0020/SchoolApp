package com.example.schoolapp.controller;

import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/getAllCourses")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping("/createCourse")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseDTO));
    }

    @PutMapping("/updateCourseById/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/deleteCourseById/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully.");
    }

    @GetMapping("/getAllCoursesWithStudents")
    public ResponseEntity<List<CourseDTO>> getAllCoursesWithStudents() {
        List<CourseDTO> coursesWithStudents = courseService.getAllCoursesWithStudents();
        return ResponseEntity.ok(coursesWithStudents);
    }

    @GetMapping("/getCoursesByStudent/{studentId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByStudent(@PathVariable int studentId) {
        List<CourseDTO> courses = courseService.getCoursesByStudentId(studentId);
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/getCoursesWithNoStudents")
    public ResponseEntity<List<CourseDTO>> getCoursesWithNoStudents() {
        List<CourseDTO> emptyCourses = courseService.getCoursesWithNoStudents();
        return ResponseEntity.ok(emptyCourses);
    }
}

