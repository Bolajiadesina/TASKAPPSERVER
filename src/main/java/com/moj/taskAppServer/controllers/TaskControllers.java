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
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getTaskById(@PathVariable Long id) {
        
        return taskService.getTaskById(id);
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
    public ResponseEntity<ResponseData> updateTask( @RequestBody Task task) {
        return taskService.updateTask(task);
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteTask(@PathVariable Long id) {
        
        return taskService.deleteTask(id);
    }
   


}
