package com.example.schoolapp;

import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.model.dto.StudentDTO;
import com.example.schoolapp.model.entity.Course;
import com.example.schoolapp.model.entity.Student;
import com.example.schoolapp.repository.StudentRepository;
import com.example.schoolapp.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void whenGetAllStudents_thenStudentDTOsShouldBeReturned() {
        // Arrange
        Student student = new Student(1, "John Doe", new HashSet<>());
        when(studentRepository.findAll()).thenReturn(List.of(student));

        // Act
        List<StudentDTO> studentDTOs = studentService.getAllStudents();

        // Assert
        assertNotNull(studentDTOs);
        assertFalse(studentDTOs.isEmpty());
        assertEquals(student.getName(), studentDTOs.get(0).getName());
    }

    @Test
    public void whenGetStudentById_withExistingId_thenStudentDTOShouldBeReturned() {
        // Arrange
        int studentId = 1;
        Student student = new Student(studentId, "John Doe", new HashSet<>());
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act
        StudentDTO studentDTO = studentService.getStudentById(studentId);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(student.getName(), studentDTO.getName());
    }

    @Test
    public void whenGetStudentById_withNonExistingId_thenThrowEntityNotFoundException() {
        // Arrange
        int studentId = 1;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(studentId));
    }

    @Test
    public void whenCreateStudent_thenStudentDTOShouldBeReturned() {
        // Arrange
        StudentDTO studentDTORequest = new StudentDTO(0, "Jane Doe", null);
        Student student = new Student(1, "Jane Doe", new HashSet<>());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        StudentDTO studentDTOResponse = studentService.createStudent(studentDTORequest);

        // Assert
        assertNotNull(studentDTOResponse);
        assertEquals(student.getName(), studentDTOResponse.getName());
    }

    @Test
    public void whenUpdateStudent_withExistingId_thenStudentDTOShouldBeReturned() {
        // Arrange
        int studentId = 1;
        Student existingStudent = new Student(studentId, "John Doe", new HashSet<>());
        StudentDTO updatedStudentDTO = new StudentDTO(studentId, "Johnny Doe", null);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        // Act
        StudentDTO studentDTO = studentService.updateStudent(studentId, updatedStudentDTO);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(updatedStudentDTO.getName(), studentDTO.getName());
    }

    @Test
    public void whenUpdateStudent_withNonExistingId_thenThrowEntityNotFoundException() {
        // Arrange
        int studentId = 1;
        StudentDTO updatedStudentDTO = new StudentDTO(studentId, "Johnny Doe", null);
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(studentId, updatedStudentDTO));
    }

    @Test
    public void whenDeleteStudent_withExistingId_thenRepositoryMethodShouldBeCalled() {
        // Arrange
        int studentId = 1;
        willDoNothing().given(studentRepository).deleteById(studentId);

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(studentRepository).deleteById(studentId);
    }
    @Test
    public void whenGetStudentsByCourseId_thenStudentDTOsShouldBeReturned() {
        // Arrange
        int courseId = 100; // Sample course ID
        Student student = new Student(1, "John Doe", new HashSet<>());
        when(studentRepository.findStudentsByCourseId(courseId)).thenReturn(List.of(student));

        // Act
        List<StudentDTO> studentDTOs = studentService.getStudentsByCourseId(courseId);

        // Assert
        assertNotNull(studentDTOs);
        assertFalse(studentDTOs.isEmpty());
        assertEquals(student.getName(), studentDTOs.get(0).getName());
    }

    @Test
    public void whenGetStudentsWithNoCourses_thenStudentDTOsShouldBeReturned() {
        // Arrange
        Student student = new Student(1, "John Doe", new HashSet<>()); // No courses
        when(studentRepository.findStudentsWithNoCourses()).thenReturn(List.of(student));

        // Act
        List<StudentDTO> studentDTOs = studentService.getStudentsWithNoCourses();

        // Assert
        assertNotNull(studentDTOs);
        assertFalse(studentDTOs.isEmpty());
        assertEquals(student.getName(), studentDTOs.get(0).getName());
        assertTrue(studentDTOs.get(0).getCourses().isEmpty());
    }

    @Test
    public void whenGetAllStudentsWithCourses_thenStudentDTOsShouldBeReturned() {
        // Arrange
        Student studentTest = new Student();
        Course course = new Course(1, "Mathematics", 30, Set.of(studentTest));
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        Student student = new Student(1, "John Doe", courses);
        when(studentRepository.findAllStudentsWithCourses()).thenReturn(List.of(student));

        // Act
        List<StudentDTO> studentDTOs = studentService.getAllStudentsWithCourses();

        // Assert
        assertNotNull(studentDTOs);
        assertFalse(studentDTOs.isEmpty());
        assertEquals(student.getName(), studentDTOs.get(0).getName());
        assertFalse(studentDTOs.get(0).getCourses().isEmpty());
    }
}

