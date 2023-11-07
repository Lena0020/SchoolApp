package com.example.schoolapp.service;

import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.model.dto.StudentDTO;
import com.example.schoolapp.model.entity.Student;
import com.example.schoolapp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(int studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return convertToStudentDTO(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToStudentEntity(studentDTO);
        Student createdStudent = studentRepository.save(student);
        return convertToStudentDTO(createdStudent);
    }

    public StudentDTO updateStudent(int studentId, StudentDTO updatedStudentDTO) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        student.setName(updatedStudentDTO.getName());
        Student updatedStudent = studentRepository.save(student);
        return convertToStudentDTO(updatedStudent);
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<StudentDTO> getStudentsByCourseId(int courseId) {
        return studentRepository.findStudentsByCourseId(courseId).stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsWithNoCourses() {
        List<Student> students = studentRepository.findStudentsWithNoCourses();
        return students.stream().map(this::convertToStudentDTO).collect(Collectors.toList());
    }

    public List<StudentDTO> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAllStudentsWithCourses();
        return students.stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO convertToStudentDTO(Student student) {
        Set<CourseDTO> courseDTOs = student.getCourses().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toSet());
        return new StudentDTO(student.getId(), student.getName(), courseDTOs);
    }

    public Student convertToStudentEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId()); // existing student
        student.setName(studentDTO.getName());
        return student;
    }
}
