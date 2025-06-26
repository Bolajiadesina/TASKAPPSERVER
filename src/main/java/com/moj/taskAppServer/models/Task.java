package com.moj.taskAppServer.models;

import java.io.Serial;

import io.micrometer.common.lang.NonNull;

public class Task {

    
    private Double id;

    @NonNull
    private String taskName;
    @NonNull
    private String taskDescription;
    @NonNull
    private String taskStatus;
    @NonNull
    private String taskDueDate;



    public Task() {
        // Default constructor
    }

    public Task(Double id, String taskName, String taskDescription, String taskStatus, String taskDueDate) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskDueDate = taskDueDate;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskDueDate='" + taskDueDate + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (taskStatus != task.taskStatus) return false;
        if (!id.equals(task.id)) return false;
        if (!taskName.equals(task.taskName)) return false;
        return taskDescription.equals(task.taskDescription);
    }
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + taskName.hashCode();
        result = 31 * result + taskDescription.hashCode();
        result = 31 * result + taskStatus.hashCode();
        result = 31 * result + taskDueDate.hashCode();
        return result;
    }
}
