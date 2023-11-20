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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Value("${application.limits.students_per_course}")
    private int studentsPerCourse;

    @Value("${application.limits.courses_per_student}")
    private int coursesPerStudent;

    @Transactional
    public void registerStudentForCourse(RegistrationDTO registrationDTO) throws CourseLimitException, StudentLimitException {
        int studentId = registrationDTO.getStudentId();
        int courseId = registrationDTO.getCourseId();

        long currentNumberOfCoursesForStudent = studentRepository.countCoursesForStudent(studentId);
        if (currentNumberOfCoursesForStudent >= coursesPerStudent) {
            throw new CourseLimitException();
        }
        long currentNumberOfStudentsForCourse = courseRepository.countStudentsForCourse(courseId);
        if (currentNumberOfStudentsForCourse >= studentsPerCourse) {
            throw new StudentLimitException();
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        student.getCourses().add(course);
        studentRepository.save(student);
    }
}


