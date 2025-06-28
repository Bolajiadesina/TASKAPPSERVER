package com.moj.taskAppServer.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;
import com.moj.taskAppServer.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskControllers {

    private final TaskService taskService;

    public TaskControllers(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseData> getTaskById(@PathVariable String taskId) {

        return taskService.getTaskById(taskId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseData> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseData> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping
    public ResponseEntity<ResponseData> updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseData> deleteTask(@PathVariable String taskId) {

        return taskService.deleteTask(taskId);
    }

}
