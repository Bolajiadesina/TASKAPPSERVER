package com.moj.taskAppServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.taskAppServer.models.Task;
import com.moj.taskAppServer.utils.StringUtilities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskAppServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private StringUtilities util;

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task();
        task.setTaskName("Integration Test Task");
        task.setTaskDescription("Integration Test Description");
        task.setTaskStatus("PENDING");
        task.setTaskDueDate("2025-07-01");

        mockMvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }

    @Test
    void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")))
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @Test
    void testGetTaskById() throws Exception {
        // First, create a task to ensure there is at least one
        String id = "44e308d2-50cc-4349-9cd4-9fc6ccc221c3";

        mockMvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("01")));
    }

    @Test
    void testUpdateTask() throws Exception {
        // First, create a task
        Task task = new Task();
        task.setTaskId("e295cef5-18b5-44b4-9a9e-ea8f1ea2d99f");
        task.setTaskName("Update Test Task");
        task.setTaskDescription("Update Test Description");
        task.setTaskStatus("ONGOING");
        task.setTaskDueDate("2025-07-01");

        // long id=59;

        String response = mockMvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Update Task
        task.setTaskDescription("Updated Description");
        mockMvc.perform(put("/api/tasks", task)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }

    @Test
    void testDeleteTaskNotFound() throws Exception {
        // First, create a task
        String taskId = "c24fdfb6-6627-4710-b02c-9171063fc7eb";

        String response = mockMvc.perform(delete("/api/tasks/taskId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskId)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Delete Task
        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("01")));
    }

    @Test
    void testDeleteTask() throws Exception {
        // First, create a task
        String taskId = "0591ec8c-adc4-4b3c-af1b-aa0aa38ef44d";

        String response = mockMvc.perform(delete("/api/tasks/taskId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskId)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Delete Task
        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("01")));
    }
   

    @Test
    void testInvalidTaskId() throws Exception {
        String invalidTaskId = "invalid-id";    

        mockMvc.perform(get("/api/tasks/{id}", invalidTaskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("400")));
    }

    @Test
    void testInvalidTaskCreation() throws Exception {
        Task invalidTask = new Task();
        invalidTask.setTaskName(""); // Missing required fields
        invalidTask.setTaskDescription("");
        invalidTask.setTaskStatus("PENDING");
        invalidTask.setTaskDueDate("2025-07-01");

        mockMvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.responseCode", is("400")));
    }


    @Test
    void testValidTaskCreation() throws Exception {
        Task validTask = new Task();
        validTask.setTaskName("Valid Task");
        validTask.setTaskDescription("Valid Description");
        validTask.setTaskStatus("PENDING");
        validTask.setTaskDueDate("2025-07-01");

        mockMvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }

}
