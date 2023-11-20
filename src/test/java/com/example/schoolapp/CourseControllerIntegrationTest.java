package com.example.schoolapp;

import com.example.schoolapp.model.dto.CourseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetAllCourses_thenStatus200() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetCourseById_thenStatus200() throws Exception {
        mockMvc.perform(get("/courses/getCourseById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenCreateCourse_thenStatus200() throws Exception {
        CourseDTO newCourse = new CourseDTO(0, "New Course", 10, null);
        mockMvc.perform(post("/courses/createCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCourse)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateCourse_thenStatus200() throws Exception {
        CourseDTO updatedCourse = new CourseDTO(1, "Updated Course", 20, null);
        mockMvc.perform(put("/courses/updateCourseById/{id}", updatedCourse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCourse)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteCourse_thenStatus200() throws Exception {
        mockMvc.perform(delete("/courses/deleteCourseById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Course deleted successfully."));
    }

    @Test
    public void whenGetAllCoursesWithStudents_thenStatus200() throws Exception {
        mockMvc.perform(get("/courses/getAllCoursesWithStudents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetCoursesByStudent_thenStatus200() throws Exception {
        mockMvc.perform(get("/courses/getCoursesByStudent/{studentId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetCoursesWithNoStudents_thenStatus200() throws Exception {
        mockMvc.perform(get("/courses/getCoursesWithNoStudents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

