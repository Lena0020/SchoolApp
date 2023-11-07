package com.example.schoolapp.exception;

public class CourseLimitException extends Throwable{
    public CourseLimitException(){
        super("Student can't be registered for more than 5 courses.");
    }
}
