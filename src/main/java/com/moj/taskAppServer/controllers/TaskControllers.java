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


    /*
     * getTaskById : retrives task using the full details of task using the taskId
     */

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseData> getTaskById(@PathVariable String taskId) {
        return taskService.getTaskById(taskId);
    }


     /*
     * getAllTasks: retrieves all task from the DB
     */
    @GetMapping("/getAll")
    public ResponseEntity<ResponseData> getAllTasks() {
        return taskService.getAllTasks();
    }

    /*
     * createTask: creates task using the required parameter(taskName,
     * taskDesription,
     * taskStatus and assigns a due date )
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseData> createTask(@RequestBody Task task) {

        return taskService.createTask(task);
    }

    /*
     * updateTask 1: updates the full details of task using the required parameter(taskName,
     * taskDesription,
     * taskStatus and assigns a due date )
     */


    @PutMapping
    public ResponseEntity<ResponseData> updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }


    /*
     * deleteTask 1: deletes task using the full details of task using the taskId(
     */


    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseData> deleteTask(@PathVariable String taskId) {
        return taskService.deleteTask(taskId);
    }

}
