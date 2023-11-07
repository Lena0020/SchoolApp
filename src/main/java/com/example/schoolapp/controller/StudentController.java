package com.example.schoolapp.controller;

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

    @GetMapping("/getAllStudents")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/createStudent")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.createStudent(studentDTO));
    }

    @PutMapping("/updateStudentById/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @DeleteMapping("/deleteStudentById/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully.");
    }

    @GetMapping("/getAllStudentsWithCourses")
    public ResponseEntity<List<StudentDTO>> getAllStudentsWithCourses() {
        List<StudentDTO> studentsWithCourses = studentService.getAllStudentsWithCourses();
        return ResponseEntity.ok(studentsWithCourses);
    }

    @GetMapping("/getStudentsByCourse/{courseId}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourse(@PathVariable int courseId) {
        List<StudentDTO> students = studentService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }
    @GetMapping("/getStudentsWithNoCourses")
    public ResponseEntity<List<StudentDTO>> getStudentsWithNoCourses() {
        List<StudentDTO> freeStudents = studentService.getStudentsWithNoCourses();
        return ResponseEntity.ok(freeStudents);
    }
}

