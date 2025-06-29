package com.moj.taskAppServer.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;
import com.moj.taskAppServer.models.TaskEnum;
import com.moj.taskAppServer.repositories.TaskRepository;
import com.moj.taskAppServer.utils.StringUtilities;

@Service
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final StringUtilities stringUtilities;

    public TaskServiceImplementation(TaskRepository taskRepository, StringUtilities stringUtilities) {
        this.taskRepository = taskRepository;
        this.stringUtilities = stringUtilities;
    }

    /*
     * getAllTasks: retrieves all task from the DB
     */
    @Override
    public ResponseEntity<ResponseData> getAllTasks() {

        ResponseData response = taskRepository.findAll();
        if (!"00".equals(response.getResponseCode())) {
            return ResponseEntity.internalServerError().body(response);
        }

        if (response.getData() == null || ((List<Task>) response.getData()).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    /*
     * createTask: creates task using the required parameter(taskName,
     * taskDesription,
     * taskStatus and assigns a due date )
     */

    @Override
    public ResponseEntity<ResponseData> createTask(Task task) {
        ResponseData response = null;

        response = taskRepository.save(task);

        if (!"00".equals(response.getResponseCode())) {
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.created(null).body(response);
    }

    /*
     * updateTask 1: updates the full details of task using the required parameter(taskName,
     * taskDesription,
     * taskStatus and assigns a due date )
     * 
     * updateTask 2: update only status
     */

    @Override
    public ResponseEntity<ResponseData> updateTask(Task task) {

        return ResponseEntity.ok(taskRepository.update(task));
    }



    /*
     * deleteTask 1: deletes task using the full details of task using the taskId(
     */

    @Override
    public ResponseEntity<ResponseData> deleteTask(String taskId) {

        ResponseData responseData = new ResponseData();

        if (!stringUtilities.validateTaskId(taskId)) {
            responseData.setResponseCode(TaskEnum.INVALID_ID.getCode());
            responseData.setResponseMessage(TaskEnum.INVALID_ID.getMessage());
            return ResponseEntity.ok(responseData);
        }

        return ResponseEntity.ok(taskRepository.delete(taskId));
    }



    /*
     * getTaskById 1: retrives task using the full details of task using the taskId
     */
    @Override
    public ResponseEntity<ResponseData> getTaskById(String taskId) {
        ResponseData responseData = new ResponseData();

        if (!stringUtilities.validateTaskId(taskId)) {
            responseData.setResponseCode(TaskEnum.INVALID_ID.getCode());
            responseData.setResponseMessage(TaskEnum.INVALID_ID.getMessage());
            return ResponseEntity.ok(responseData);
        }

        responseData = taskRepository.findById(taskId);
        if (!"00".equals(responseData.getResponseCode())) {

            return ResponseEntity.ok(responseData);
        }
        return ResponseEntity.ok(responseData);
    }

}
