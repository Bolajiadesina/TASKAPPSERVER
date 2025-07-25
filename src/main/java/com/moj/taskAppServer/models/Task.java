package com.moj.taskAppServer.models;

import java.io.Serial;

import io.micrometer.common.lang.NonNull;

public class Task {

    private String taskId;

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

    public Task(String taskId, String taskName, String taskDescription, String taskStatus, String taskDueDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskDueDate = taskDueDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskDueDate='" + taskDueDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;

        Task task = (Task) o;

        if (taskStatus != task.taskStatus)
            return false;
        if (!taskId.equals(task.taskId))
            return false;
        if (!taskName.equals(task.taskName))
            return false;
        return taskDescription.equals(task.taskDescription);
    }

    @Override
    public int hashCode() {
        int result = taskId.hashCode();
        result = 31 * result + taskName.hashCode();
        result = 31 * result + taskDescription.hashCode();
        result = 31 * result + taskStatus.hashCode();
        result = 31 * result + taskDueDate.hashCode();
        return result;
    }
}
