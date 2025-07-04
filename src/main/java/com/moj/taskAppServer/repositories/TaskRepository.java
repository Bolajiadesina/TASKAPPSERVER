package com.moj.taskAppServer.repositories;

import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.stereotype.Repository;

import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;


@Repository
public interface TaskRepository {

    ResponseData save(Task task);

    ResponseData findById(String taskIdid);

    ResponseData findAll();

    ResponseData update(Task task);

    ResponseData delete(String taskId);

}
