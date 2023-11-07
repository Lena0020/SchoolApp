package com.example.schoolapp;

import com.example.schoolapp.model.dto.StudentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetAllStudents_thenStatus200() throws Exception {
        mockMvc.perform(get("/students/getAllStudents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetStudentById_thenStatus200() throws Exception {
        mockMvc.perform(get("/students/getStudentById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenCreateStudent_thenStatus200() throws Exception {
        StudentDTO newStudent = new StudentDTO(0, "New Student", null);
        mockMvc.perform(post("/students/createStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateStudent_thenStatus200() throws Exception {
        StudentDTO updatedStudent = new StudentDTO(1, "Updated Student", null);
        mockMvc.perform(put("/students/updateStudentById/{id}", updatedStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteStudent_thenStatus200() throws Exception {
        mockMvc.perform(delete("/students/deleteStudentById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted successfully."));
    }

    @Test
    public void whenGetAllStudentsWithCourses_thenStatus200() throws Exception {
        mockMvc.perform(get("/students/getAllStudentsWithCourses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetStudentsByCourse_thenStatus200() throws Exception {
        mockMvc.perform(get("/students/getStudentsByCourse/{courseId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetStudentsWithNoCourses_thenStatus200() throws Exception {
        mockMvc.perform(get("/students/getStudentsWithNoCourses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
