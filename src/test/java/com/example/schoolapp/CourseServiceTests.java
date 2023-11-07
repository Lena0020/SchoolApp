package com.example.schoolapp;
import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.model.entity.Course;
import com.example.schoolapp.model.entity.Student;
import com.example.schoolapp.repository.CourseRepository;
import com.example.schoolapp.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void whenGetAllCourses_thenCourseDTOsShouldBeReturned() {
        // Arrange
        Course course = new Course(1, "Mathematics", 30, Collections.emptySet());
        when(courseRepository.findAll()).thenReturn(List.of(course));

        // Act
        List<CourseDTO> courseDTOs = courseService.getAllCourses();

        // Assert
        assertNotNull(courseDTOs);
        assertFalse(courseDTOs.isEmpty());
        assertEquals(course.getName(), courseDTOs.get(0).getName());
    }

    @Test
    public void whenGetCourseById_withExistingId_thenCourseDTOShouldBeReturned() {
        // Arrange
        int courseId = 1;
        Course course = new Course(courseId, "Mathematics", 30, Collections.emptySet());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        CourseDTO courseDTO = courseService.getCourseById(courseId);

        // Assert
        assertNotNull(courseDTO);
        assertEquals(course.getName(), courseDTO.getName());
    }

    @Test
    public void whenGetCourseById_withNonExistingId_thenThrowEntityNotFoundException() {
        // Arrange
        int courseId = 1;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.getCourseById(courseId));
    }

    @Test
    public void whenCreateCourse_thenCourseDTOShouldBeReturned() {
        // Arrange
        CourseDTO courseDTORequest = new CourseDTO(0, "Physics", 25, null);
        Course course = new Course(1, "Physics", 25, Collections.emptySet());
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        CourseDTO courseDTOResponse = courseService.createCourse(courseDTORequest);

        // Assert
        assertNotNull(courseDTOResponse);
        assertEquals(course.getName(), courseDTOResponse.getName());
    }

    @Test
    public void whenUpdateCourse_withExistingId_thenCourseDTOShouldBeReturned() {
        // Arrange
        int courseId = 1;
        Course existingCourse = new Course(courseId, "Mathematics", 30, Collections.emptySet());
        CourseDTO updatedCourseDTO = new CourseDTO(courseId, "Mathematics - Advanced", 30, null);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

        // Act
        CourseDTO courseDTO = courseService.updateCourse(courseId, updatedCourseDTO);

        // Assert
        assertNotNull(courseDTO);
        assertEquals(updatedCourseDTO.getName(), courseDTO.getName());
    }

    @Test
    public void whenUpdateCourse_withNonExistingId_thenThrowEntityNotFoundException() {
        // Arrange
        int courseId = 1;
        CourseDTO updatedCourseDTO = new CourseDTO(courseId, "Mathematics - Advanced", 30, null);
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.updateCourse(courseId, updatedCourseDTO));
    }

    @Test
    public void whenDeleteCourse_withExistingId_thenRepositoryMethodShouldBeCalled() {
        // Arrange
        int courseId = 1;
        willDoNothing().given(courseRepository).deleteById(courseId);

        // Act
        courseService.deleteCourse(courseId);

        // Assert
        verify(courseRepository).deleteById(courseId);
    }


    @Test
    public void whenGetCoursesByStudentId_thenCourseDTOsShouldBeReturned() {
        // Arrange
        int studentId = 1;
        Course courseTest = new Course();
        Course course = new Course(1, "Mathematics", 50, Set.of(new Student(studentId, "John Doe", Set.of(courseTest))));
        when(courseRepository.findCoursesByStudentId(studentId)).thenReturn(List.of(course));

        // Act
        List<CourseDTO> courseDTOs = courseService.getCoursesByStudentId(studentId);

        // Assert
        assertNotNull(courseDTOs);
        assertFalse(courseDTOs.isEmpty());
        assertEquals(course.getName(), courseDTOs.get(0).getName());
    }

    @Test
    public void whenGetAllCoursesWithStudents_thenCourseDTOsShouldBeReturned() {
        // Arrange
        Course courseTest = new Course();
        Student student = new Student(1, "John Doe", Set.of(courseTest));
        Course course = new Course(1, "Mathematics", 30, Set.of(student));
        when(courseRepository.findAllCoursesWithStudents()).thenReturn(List.of(course));

        // Act
        List<CourseDTO> courseDTOs = courseService.getAllCoursesWithStudents();

        // Assert
        assertNotNull(courseDTOs);
        assertFalse(courseDTOs.isEmpty());
        assertEquals(course.getName(), courseDTOs.get(0).getName());
        assertFalse(courseDTOs.get(0).getStudents().isEmpty());
    }

    @Test
    public void whenGetCoursesWithNoStudents_thenCourseDTOsShouldBeReturned() {
        // Arrange
        Course course = new Course(1, "Mathematics", 30, Collections.emptySet());
        when(courseRepository.findCoursesWithNoStudents()).thenReturn(List.of(course));

        // Act
        List<CourseDTO> courseDTOs = courseService.getCoursesWithNoStudents();

        // Assert
        assertNotNull(courseDTOs);
        assertFalse(courseDTOs.isEmpty());
        assertEquals(course.getName(), courseDTOs.get(0).getName());
        assertTrue(courseDTOs.get(0).getStudents().isEmpty());
    }
}
