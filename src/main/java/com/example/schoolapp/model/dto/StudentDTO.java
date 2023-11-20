package com.example.schoolapp.model.dto;

import com.example.schoolapp.model.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private int id;
    private String name;
    private Set<CourseDTO> courses;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
    }
}
