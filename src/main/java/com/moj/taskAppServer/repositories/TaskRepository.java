package com.moj.taskAppServer.repositories;

import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.stereotype.Repository;

import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;


@Repository
public interface TaskRepository {

    ResponseData save(Task task);

    ResponseData findById(Long id);

    ResponseData findAll();

    ResponseData update(Long id, Task task);

    ResponseData delete(Long id);

}
