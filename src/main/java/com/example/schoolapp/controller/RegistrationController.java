package com.example.schoolapp.controller;

import com.example.schoolapp.exception.CourseLimitException;
import com.example.schoolapp.exception.StudentLimitException;
import com.example.schoolapp.model.dto.RegistrationDTO;
import com.example.schoolapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/registerStudentForCourse")
    public ResponseEntity<?> registerStudentForCourse(@RequestBody RegistrationDTO registrationDTO) throws StudentLimitException, CourseLimitException {
        registrationService.registerStudentForCourse(registrationDTO);
        return ResponseEntity.ok("Student registered for course successfully.");
    }
}
