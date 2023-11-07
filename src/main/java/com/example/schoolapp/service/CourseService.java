package com.example.schoolapp.service;

import com.example.schoolapp.model.dto.CourseDTO;
import com.example.schoolapp.model.dto.StudentDTO;
import com.example.schoolapp.model.entity.Course;
import com.example.schoolapp.model.entity.Student;
import com.example.schoolapp.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(int courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return convertToCourseDTO(course.get());
        } else {
            throw new EntityNotFoundException("Course not found");
        }
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToCourseEntity(courseDTO);
        Course createdCourse = courseRepository.save(course);
        return convertToCourseDTO(createdCourse);
    }

    public CourseDTO updateCourse(int courseId, CourseDTO updatedCourseDTO) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Course existingCourse = course.get();
            existingCourse.setName(updatedCourseDTO.getName());
            existingCourse.setCapacity(updatedCourseDTO.getCapacity());
            Course updatedCourse = courseRepository.save(existingCourse);
            return convertToCourseDTO(updatedCourse);
        } else {
            throw new EntityNotFoundException("Course not found");
        }
    }

    public void deleteCourse(int courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<CourseDTO> getCoursesByStudentId(int studentId) {
        return courseRepository.findCoursesByStudentId(studentId).stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getAllCoursesWithStudents() {
        List<Course> courses = courseRepository.findAllCoursesWithStudents();
        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesWithNoStudents() {
        List<Course> courses = courseRepository.findCoursesWithNoStudents();
        return courses.stream().map(this::convertToCourseDTO).collect(Collectors.toList());
    }

    public CourseDTO convertToCourseDTO(Course course) {
        Set<StudentDTO> studentDTOs = course.getStudents().stream()
                .map(StudentDTO::new)
                .collect(Collectors.toSet());
        return new CourseDTO(course.getId(), course.getName(), course.getCapacity(), studentDTOs);
    }

    public Course convertToCourseEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId()); //existing course
        course.setName(courseDTO.getName());
        course.setCapacity(courseDTO.getCapacity());
        return course;
    }
}
