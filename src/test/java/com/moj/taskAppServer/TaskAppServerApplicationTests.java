package com.moj.taskAppServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.taskAppServer.models.Task;
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


	 @Test
    void testCreateTask() throws Exception {
        Task task = new Task();
        task.setTaskName("Integration Test Task");
        task.setTaskDescription("Integration Test Description");
        task.setTaskStatus("NEW");
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
        long id=59;

        mockMvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }

    @Test
    void testUpdateTask() throws Exception {
        // First, create a task
        Task task = new Task();
        task.setTaskName("Update Test Task");
        task.setTaskDescription("Update Test Description");
        task.setTaskStatus("NEW");
        task.setTaskDueDate("2025-07-01");


		long id=59;

        String response = mockMvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

       

        // Update Task
        task.setTaskDescription("Updated Description");
        mockMvc.perform(put("/api/tasks/{id}",id,task)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }

    @Test
    void testDeleteTask() throws Exception {
        // First, create a task
        Long id= (long) 65;

        String response = mockMvc.perform(delete("/api/tasks/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(id)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

       

        // Delete Task
        mockMvc.perform(delete("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }

   
}
