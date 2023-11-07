package com.example.schoolapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CourseLimitException.class)
    public ResponseEntity<ApiError> CourseLimitHandler(CourseLimitException e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StudentLimitException.class)
    public ResponseEntity<ApiError> StudentLimitHandler(StudentLimitException e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.CONFLICT);
    }
}
