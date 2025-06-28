package com.moj.taskAppServer.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;

@Service
public interface TaskService {
    ResponseEntity<ResponseData> getAllTasks();

    ResponseEntity<ResponseData> createTask(Task task);

    ResponseEntity<ResponseData> updateTask( Task task);

    ResponseEntity<ResponseData> deleteTask(String taskId);

    ResponseEntity<ResponseData> getTaskById(String taskId);
}
