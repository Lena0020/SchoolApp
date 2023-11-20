package com.example.schoolapp.model.dto;

import com.example.schoolapp.model.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private int id;
    private String name;
    private int capacity;
    private Set<StudentDTO> students;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.capacity = course.getCapacity();
    }
}
