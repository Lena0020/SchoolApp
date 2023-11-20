package com.example.schoolapp.controller;

import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.model.dto.StudentDTO;
import com.example.schoolapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(name = "withCourses", required = false) Boolean withCourses,
            @RequestParam(name = "courseId", required = false) Integer courseId
    ) {
        if (withCourses == null && courseId == null) {
            return ResponseEntity.ok(studentService.getAllStudents());
        } else {
            if (courseId != null) {
                List<StudentDTO> students = studentService.getStudentsByCourseId(courseId);
                return ResponseEntity.ok(students);
            } else if (withCourses) {
                List<StudentDTO> studentsWithCourses = studentService.getAllStudentsWithCourses();
                return ResponseEntity.ok(studentsWithCourses);
            } else {
                List<StudentDTO> freeStudents = studentService.getStudentsWithNoCourses();
                return ResponseEntity.ok(freeStudents);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.createStudent(studentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully.");
    }
}

