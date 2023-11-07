package com.example.schoolapp.repository;

import com.example.schoolapp.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findById(int id);

    @Query("SELECT COUNT(cs) FROM Course c JOIN c.students cs WHERE c.id = :courseId")
    long countStudentsForCourse(@Param("courseId") int courseId);

    @Query("SELECT c FROM Course c JOIN FETCH c.students")
    List<Course> findAllCoursesWithStudents();

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") int studentId);

    @Query("SELECT c FROM Course c WHERE c.students IS EMPTY")
    List<Course> findCoursesWithNoStudents();

}
