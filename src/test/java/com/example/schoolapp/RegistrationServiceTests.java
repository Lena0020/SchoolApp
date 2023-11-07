package com.example.schoolapp;

import com.example.schoolapp.exception.CourseLimitException;
import com.example.schoolapp.exception.StudentLimitException;
import com.example.schoolapp.model.dto.RegistrationDTO;
import com.example.schoolapp.model.entity.Course;
import com.example.schoolapp.model.entity.Student;
import com.example.schoolapp.repository.CourseRepository;
import com.example.schoolapp.repository.StudentRepository;
import com.example.schoolapp.service.RegistrationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private RegistrationService registrationService;

    // These entities will be reused across different tests
    private Student student;
    private Course course;

    @BeforeEach
    public void setUp() {
        student = new Student(1, "John Doe", new HashSet<>());
        course = new Course(1, "Mathematics", 30, new HashSet<>());
    }

    @Test
    public void whenRegisterStudentForCourse_thenShouldRegisterSuccessfully() throws StudentLimitException, CourseLimitException {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO(student.getId(), course.getId());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(studentRepository.countCoursesForStudent(student.getId())).thenReturn(4L);
        when(courseRepository.countStudentsForCourse(course.getId())).thenReturn(49L);

        // Act
        registrationService.registerStudentForCourse(registrationDTO);

        // Assert
        assertTrue(student.getCourses().contains(course));
        verify(studentRepository).save(student);
    }

    @Test
    public void whenRegisterStudentForCourseWithCourseLimitReached_thenThrowCourseLimitException() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO(student.getId(), course.getId());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(studentRepository.countCoursesForStudent(student.getId())).thenReturn(5L);

        // Act & Assert
        assertThrows(CourseLimitException.class, () -> registrationService.registerStudentForCourse(registrationDTO));
    }

    @Test
    public void whenRegisterStudentForCourseWithStudentLimitReached_thenThrowStudentLimitException() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO(student.getId(), course.getId());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepository.countStudentsForCourse(course.getId())).thenReturn(50L);

        // Act & Assert
        assertThrows(StudentLimitException.class, () -> registrationService.registerStudentForCourse(registrationDTO));
    }

    @Test
    public void whenRegisterStudentForCourseWithNonExistingStudent_thenThrowEntityNotFoundException() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO(2, course.getId()); // Non-existing student ID
        when(studentRepository.findById(registrationDTO.getStudentId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> registrationService.registerStudentForCourse(registrationDTO));
    }
}
