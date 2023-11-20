package com.example.schoolapp.controller;

import com.example.schoolapp.exception.CourseLimitException;
import com.example.schoolapp.exception.StudentLimitException;
import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.model.dto.RegistrationDTO;
import com.example.schoolapp.service.CourseService;
import com.example.schoolapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<?> getAllCourses(
            @RequestParam(name = "withStudents", required = false) Boolean withStudents,
            @RequestParam(name = "studentId", required = false) Integer studentId
    ) {
        if (withStudents == null && studentId == null) {
            return ResponseEntity.ok(courseService.getAllCourses());
        } else {
            if (studentId != null) {
                List<CourseDTO> courses = courseService.getCoursesByStudentId(studentId);
                return ResponseEntity.ok(courses);
            } else if (withStudents) {
                List<CourseDTO> coursesWithStudents = courseService.getAllCoursesWithStudents();
                return ResponseEntity.ok(coursesWithStudents);
            } else {
                List<CourseDTO> emptyCourses = courseService.getCoursesWithNoStudents();
                return ResponseEntity.ok(emptyCourses);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully.");
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> registerStudentForCourse(@Validated @RequestBody RegistrationDTO registrationDTO) throws StudentLimitException, CourseLimitException {
        registrationService.registerStudentForCourse(registrationDTO);
        return ResponseEntity.ok("Student registered for course successfully.");
    }
}

