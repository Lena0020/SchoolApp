package com.example.schoolapp.service;

import com.example.schoolapp.exception.CourseLimitException;
import com.example.schoolapp.exception.StudentLimitException;
import com.example.schoolapp.model.dto.RegistrationDTO;
import com.example.schoolapp.model.entity.Course;
import com.example.schoolapp.model.entity.Student;
import com.example.schoolapp.repository.CourseRepository;
import com.example.schoolapp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void registerStudentForCourse(RegistrationDTO registrationDTO) throws CourseLimitException, StudentLimitException {
        int studentId = registrationDTO.getStudentId();
        int courseId = registrationDTO.getCourseId();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        long currentNumberOfCoursesForStudent = studentRepository.countCoursesForStudent(studentId);
        if (currentNumberOfCoursesForStudent >= 5) {
            throw new CourseLimitException();
        }

        long currentNumberOfStudentsForCourse = courseRepository.countStudentsForCourse(courseId);
        if (currentNumberOfStudentsForCourse >= 50) {
            throw new StudentLimitException();
        }

        student.getCourses().add(course);
        studentRepository.save(student);
        // saving the student will automatically update the course due to the relationship mapping
    }
}


