package com.example.schoolapp.repository;

import com.example.schoolapp.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    Optional<Student> findById(int id);

    @Query("SELECT COUNT(sc) FROM Student s JOIN s.courses sc WHERE s.id = :studentId")
    long countCoursesForStudent(@Param("studentId") int studentId);

    @Query("SELECT s FROM Student s JOIN FETCH s.courses")
    List<Student> findAllStudentsWithCourses();

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") int courseId);

    @Query("SELECT s FROM Student s WHERE s.courses IS EMPTY")
    List<Student> findStudentsWithNoCourses();

}
