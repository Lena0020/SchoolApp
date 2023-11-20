package com.example.schoolapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
//    @CanStudentEnrollForCourse
    private int studentId;
    private int courseId;
}

