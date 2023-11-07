package com.example.schoolapp.exception;

public class StudentLimitException extends Throwable{
    public StudentLimitException(){
        super("Course can't have more than 50 students.");
    }
}
