package com.moj.taskAppServer.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class StringUtilities {


   public boolean validateTaskId(String taskId){
    String regexString = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    Pattern pattern = Pattern.compile(regexString);
    if (taskId == null) return false;
    return pattern.matcher(taskId).matches();

   }


  public String reverseDate(String inputDate) {
    if (inputDate == null) return "";
    String[] parts = inputDate.split("-");
    if (parts.length != 3) return inputDate; // return as is if not in expected format
    return parts[2] + "-" + parts[1] + "-" + parts[0];
}
}
