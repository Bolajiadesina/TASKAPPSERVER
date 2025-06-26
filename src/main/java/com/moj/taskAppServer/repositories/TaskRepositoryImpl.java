package com.moj.taskAppServer.repositories;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.moj.taskAppServer.models.ResponseData;
import com.moj.taskAppServer.models.Task;
import com.moj.taskAppServer.models.TaskEnum;
import com.moj.taskAppServer.utils.TaskDatabase;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private TaskDatabase taskDatabase;

    public TaskRepositoryImpl(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    @Override
    public ResponseData save(Task task) {
        // Implementation for saving a task
        if (task == null || task.getTaskName() == null || task.getTaskDescription() == null
                || task.getTaskStatus() == null
                || task.getTaskDueDate() == null) {
            System.out.println("task: " + task);
            return new ResponseData(TaskEnum.NULL_OBJECT.getMessage(), TaskEnum.NULL_OBJECT.getCode(), null);
        }

        try (Connection conn = taskDatabase.connect()) {
            // Prepare the call
            String sql = "{ ? = call public.save_task(?, ?, ?, ?) }";

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                // Set input parameters
                stmt.registerOutParameter(1, Types.VARCHAR); // return code

                // Set input parameters
                stmt.setString(2, task.getTaskName());
                stmt.setString(3, task.getTaskDescription());
                stmt.setString(4, task.getTaskStatus().toUpperCase());
                stmt.setString(5, task.getTaskDueDate());

                stmt.execute();

                // Get the returned value (e.g., new task ID)
                String returnCode = stmt.getString(1);

                if ("00".equals(returnCode)) {
                    return new ResponseData(TaskEnum.SUCCESS.getMessage(), TaskEnum.SUCCESS.getCode(), task);
                } else {
                    // If there was an error, return the error message
                    return new ResponseData(TaskEnum.FAILED_OPERATION.getMessage(), TaskEnum.FAILED_OPERATION.getCode(),
                            null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseData("Database error: " + e.getMessage(), TaskEnum.SQL_EXCEPTION.getCode(), null);
        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();
            return new ResponseData("Class not found: " + e1.getMessage(), TaskEnum.CLASS_NOT_FOUND_EXCEPTION.getCode(),
                    null);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
            return new ResponseData("Instantiation error: " + e1.getMessage(),
                    TaskEnum.INSTANTIATION_EXCEPTION.getCode(), null);
        } catch (IllegalAccessException e1) {

            e1.printStackTrace();
            return new ResponseData("Illegal access error: " + e1.getMessage(),
                    TaskEnum.ILLEGAL_ACCESS_EXCEPTION.getCode(), null);
        } catch (Exception e1) {

            e1.printStackTrace();

            return new ResponseData("Unknown error: " + e1.getMessage(), TaskEnum.GENERIC_EXCEPTION.getCode(), null);
        }

    }

    @Override
    public ResponseData findById(Long id) {
        if (id == null) {
            return new ResponseData(TaskEnum.NULL_OBJECT.getMessage(), TaskEnum.NULL_OBJECT.getCode(), null);
        }

        try (Connection conn = taskDatabase.connect()) {

            conn.setAutoCommit(false);
            // Prepare the call with proper syntax for procedure with OUT parameters
            String sql = "call public.get_task_by_id(?,?, ?)";

            try (CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setLong(1, id);

                // Register OUT parameters in the correct order
                stmt.registerOutParameter(2, Types.VARCHAR); // p_response_code
                stmt.registerOutParameter(3, Types.REF_CURSOR); // p_task_cursor

                // Execute the procedure
                stmt.execute();

                // Get response code
                String code = stmt.getString(2);

                if ("00".equals(code)) {
                    // Process cursor results
                    Task task = null;
                    try (ResultSet rs = stmt.getObject(3, ResultSet.class)) {
                        while (rs.next()) {
                            task = new Task();
                            task.setId(rs.getDouble("task_id"));
                            task.setTaskName(rs.getString("task_title"));
                            task.setTaskStatus(rs.getString("task_status"));
                            task.setTaskDescription(rs.getString("task_description"));
                            task.setTaskDueDate(rs.getString("task_due_date"));

                        }
                        return new ResponseData(TaskEnum.SUCCESS.getMessage(),
                                TaskEnum.SUCCESS.getCode(),
                                task);
                    }
                } else if ("01".equals(code)) {
                    return new ResponseData(TaskEnum.NOT_FOUND.getMessage(), TaskEnum.NOT_FOUND.getCode(), null);
                } else {
                    return new ResponseData(TaskEnum.FAILED_OPERATION.getMessage(),
                            TaskEnum.FAILED_OPERATION.getCode(),
                            null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("Database error: " + e.getMessage(),
                    TaskEnum.GENERIC_EXCEPTION.getCode(),
                    null);
        }
    }

    @Override
    public ResponseData findAll() {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = taskDatabase.connect()) {

            conn.setAutoCommit(false);
            // Prepare the call with proper syntax for procedure with OUT parameters
            String sql = "call public.get_all_tasks(?, ?)";

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                // Register OUT parameters in the correct order
                stmt.registerOutParameter(1, Types.VARCHAR); // p_response_code
                stmt.registerOutParameter(2, Types.REF_CURSOR); // p_task_cursor

                // Execute the procedure
                stmt.execute();

                // Get response code
                String code = stmt.getString(1);

                if ("00".equals(code)) {
                    // Process cursor results
                    try (ResultSet rs = stmt.getObject(2, ResultSet.class)) {
                        while (rs.next()) {
                            Task task = new Task();
                            task.setId(rs.getDouble("task_id"));
                            task.setTaskName(rs.getString("task_title"));
                            task.setTaskStatus(rs.getString("task_status"));
                            task.setTaskDescription(rs.getString("task_description"));
                            task.setTaskDueDate(rs.getString("task_due_date"));
                            tasks.add(task);
                        }
                        return new ResponseData(TaskEnum.SUCCESS.getMessage(),
                                TaskEnum.SUCCESS.getCode(),
                                tasks);
                    }
                } else {
                    return new ResponseData(TaskEnum.FAILED_OPERATION.getMessage(),
                            TaskEnum.FAILED_OPERATION.getCode(),
                            null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("Database error: " + e.getMessage(),
                    TaskEnum.GENERIC_EXCEPTION.getCode(),
                    null);
        }
    }

    @Override
    public ResponseData update(Task task) {
        if (task == null || task.getTaskName() == null || task.getTaskDescription() == null
                || task.getTaskStatus() == null
                || task.getTaskDueDate() == null) {

            return new ResponseData(TaskEnum.NULL_OBJECT.getMessage(), TaskEnum.NULL_OBJECT.getCode(), null);
        }

        try (Connection conn = taskDatabase.connect()) {
            // Prepare the call
            String sql = "{ ? = call public.update_task(?, ?,?, ?, ?) }";

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                // Set input parameters
                stmt.registerOutParameter(1, Types.VARCHAR);
                // Set input parameters
                stmt.setDouble(2, task.getId());
                stmt.setString(3, task.getTaskName());
                stmt.setString(4, task.getTaskDescription());
                stmt.setString(5, task.getTaskStatus().toUpperCase());
                stmt.setString(6, task.getTaskDueDate());

                stmt.execute();

                // Get the returned value (e.g., new task ID)
                String returnCode = stmt.getString(1);

                if ("00".equals(returnCode)) {
                    return new ResponseData(TaskEnum.SUCCESS.getMessage(), TaskEnum.SUCCESS.getCode(), task);
                } else {
                    // If there was an error, return the error message
                    return new ResponseData(TaskEnum.FAILED_OPERATION.getMessage(), TaskEnum.FAILED_OPERATION.getCode(),
                            null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseData("Database error: " + e.getMessage(), TaskEnum.SQL_EXCEPTION.getCode(), null);
        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();
            return new ResponseData("Class not found: " + e1.getMessage(), TaskEnum.CLASS_NOT_FOUND_EXCEPTION.getCode(),
                    null);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
            return new ResponseData("Instantiation error: " + e1.getMessage(),
                    TaskEnum.INSTANTIATION_EXCEPTION.getCode(), null);
        } catch (IllegalAccessException e1) {

            e1.printStackTrace();
            return new ResponseData("Illegal access error: " + e1.getMessage(),
                    TaskEnum.ILLEGAL_ACCESS_EXCEPTION.getCode(), null);
        } catch (Exception e1) {

            e1.printStackTrace();

            return new ResponseData("Unknown error: " + e1.getMessage(), TaskEnum.GENERIC_EXCEPTION.getCode(), null);
        }
    }

    @Override
    public ResponseData delete(Long taskId) {
        try (Connection conn = taskDatabase.connect()) {

            String sql = "{ ? = call public.delete_task_by_id(?) }";

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                // Register return parameter
                stmt.registerOutParameter(1, Types.VARCHAR);

                // Set input parameter
                stmt.setLong(2, taskId);

                // Execute
                stmt.execute();

                // Get response code
                String code = stmt.getString(1);

                switch (code) {
                    case "00":
                        return new ResponseData("Task deleted successfully",
                                TaskEnum.SUCCESS.getCode(),
                                null);
                    case "01":
                        return new ResponseData(TaskEnum.NOT_FOUND.getMessage(),
                                TaskEnum.NOT_FOUND.getCode(),
                                null);
                    default:
                        return new ResponseData(TaskEnum.NOT_FOUND.getMessage(),
                                TaskEnum.FAILED_OPERATION.getCode(),
                                null);
                }
            }
        } catch (Exception e) {
            return new ResponseData("Database error: " + e.getMessage(),
                    TaskEnum.GENERIC_EXCEPTION.getCode(),
                    null);
        }
    }

}
