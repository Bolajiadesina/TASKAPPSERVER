package com.moj.taskAppServer.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;
import com.moj.taskAppServer.repositories.TaskRepository;

@Service
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Implement the methods defined in the TaskService interface
    @Override
    public ResponseEntity<ResponseData> getAllTasks() {
        // Logic to retrieve all tasks
       
        ResponseData response = taskRepository.findAll();
        if (!"00".equals(response.getResponseCode())) {
            return ResponseEntity.internalServerError().body(response);
        }

        if (response.getData() == null || ((List<Task>) response.getData()).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResponseData> createTask(Task task) {
        ResponseData response=null;
         int count=5;
        for (int i=0; i<=count; i++) {
              response = taskRepository.save(task);
        }
      
        if (!"00".equals(response.getResponseCode())) {
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.created(null).body(response);
    }

    @Override
    public ResponseEntity<ResponseData> updateTask(Long id, Task task) {
        
        return ResponseEntity.ok(taskRepository.update(id, task)); 
    }

    @Override
    public ResponseEntity<ResponseData> deleteTask(Long id) {

        return ResponseEntity.ok(taskRepository.delete(id));
    }

    @Override
    public ResponseEntity<ResponseData> getTaskById(Long id) {
        
     
        ResponseData response = taskRepository.findById(id);
        if (!"00".equals(response.getResponseCode())) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

}
