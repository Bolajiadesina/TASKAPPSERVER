package com.moj.taskAppServer.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.moj.taskAppServer.models.Task;

@Service
public class StringUtilities {

  public boolean validateTaskId(String taskId) {
    String regexString = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    Pattern pattern = Pattern.compile(regexString);
    if (taskId == null)
      return false;
    return pattern.matcher(taskId).matches();

  }

  public String reverseDate(String inputDate) {
    if (inputDate == null)
      return "";
    String[] parts = inputDate.split("-");
    if (parts.length != 3)
      return inputDate; // return as is if not in expected format
    return parts[2] + "-" + parts[1] + "-" + parts[0];
  }

  public boolean validateTask(Task task) {

    if (task == null) {
      return false;
    }
    if ((task.getTaskName() == null || task.getTaskName().isEmpty())
        || (task.getTaskDescription() == null || task.getTaskDescription().isEmpty())
        || (task.getTaskStatus() == null || task.getTaskStatus().isEmpty())
        || (task.getTaskDueDate() == null || task.getTaskDueDate().isEmpty())) {
      return false;
    }
    return true;

  }

  
}
