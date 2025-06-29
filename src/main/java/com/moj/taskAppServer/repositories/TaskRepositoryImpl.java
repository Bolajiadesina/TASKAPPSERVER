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
import com.moj.taskAppServer.utils.StringUtilities;
import com.moj.taskAppServer.utils.TaskDatabase;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private TaskDatabase taskDatabase;
    private StringUtilities utils;

    public TaskRepositoryImpl(TaskDatabase taskDatabase, StringUtilities utils) {
        this.taskDatabase = taskDatabase;
        this.utils = utils;
    }

    @Override
    public ResponseData save(Task task) {

        if (task == null || task.getTaskName() == null || task.getTaskDescription() == null
                || task.getTaskStatus() == null
                || task.getTaskDueDate() == null) {

            return new ResponseData(TaskEnum.NULL_OBJECT.getMessage(), TaskEnum.NULL_OBJECT.getCode(), null);
        }

        try (Connection conn = taskDatabase.connect()) {

            String sql = "{ ? = call public.save_task(?, ?, ?, ?) }";

            try (CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.registerOutParameter(1, Types.VARCHAR);

                stmt.setString(2,
                        (task.getTaskName() == null || task.getTaskName().isEmpty()) ? "" : task.getTaskName());
                stmt.setString(3, (task.getTaskDescription() == null || task.getTaskDescription().isEmpty()) ? ""
                        : task.getTaskDescription());
                stmt.setString(4, (task.getTaskStatus() == null || task.getTaskStatus().isEmpty()) ? ""
                        : task.getTaskStatus().toUpperCase());
                stmt.setString(5, (task.getTaskDueDate() == null || task.getTaskDueDate().isEmpty()) ? ""
                        : utils.reverseDate(task.getTaskDueDate()));

                stmt.execute();

                String returnCode = stmt.getString(1);

                if ("00".equals(returnCode)) {
                    return new ResponseData(TaskEnum.CREATED.getMessage(), TaskEnum.CREATED.getCode(), task);
                } else {

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
    public ResponseData findById(String taskId) {
        if (taskId == null) {
            return new ResponseData(TaskEnum.NULL_OBJECT.getMessage(), TaskEnum.NULL_OBJECT.getCode(), null);
        }

        try (Connection conn = taskDatabase.connect()) {

            conn.setAutoCommit(false);

            String sql = "call public.get_task_by_id(?,?, ?)";

            try (CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setString(1, taskId);

                stmt.registerOutParameter(2, Types.VARCHAR);
                stmt.registerOutParameter(3, Types.REF_CURSOR);

                stmt.execute();

                String code = stmt.getString(2);

                if ("00".equals(code)) {

                    Task task = null;
                    try (ResultSet rs = stmt.getObject(3, ResultSet.class)) {
                        while (rs.next()) {
                            task = new Task();
                            task.setTaskId(rs.getString("task_id"));
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

            String sql = "call public.get_all_tasks(?, ?)";

            try (CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.registerOutParameter(1, Types.VARCHAR);
                stmt.registerOutParameter(2, Types.REF_CURSOR);

                stmt.execute();

                String code = stmt.getString(1);

                if ("00".equals(code)) {

                    try (ResultSet rs = stmt.getObject(2, ResultSet.class)) {
                        while (rs.next()) {
                            Task task = new Task();
                            task.setTaskId(rs.getString("task_id"));
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
        if (task == null || task.getTaskId() == null || task.getTaskName() == null || task.getTaskDescription() == null
                || task.getTaskStatus() == null
                || task.getTaskDueDate() == null) {

            return new ResponseData(TaskEnum.NULL_OBJECT.getMessage(), TaskEnum.NULL_OBJECT.getCode(), null);
        }

        try (Connection conn = taskDatabase.connect()) {

            String sql = "{ ? = call public.update_task(?,?,?,?,?)}";

            try (CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.registerOutParameter(1, Types.VARCHAR);

                stmt.setString(2, (task.getTaskId() == null || task.getTaskId().isEmpty()) ? "" : task.getTaskId());
                stmt.setString(3,
                        (task.getTaskName() == null || task.getTaskName().isEmpty()) ? "" : task.getTaskName());
                stmt.setString(4, (task.getTaskDescription() == null || task.getTaskDescription().isEmpty()) ? ""
                        : task.getTaskDescription());
                stmt.setString(5, (task.getTaskStatus() == null || task.getTaskStatus().isEmpty()) ? ""
                        : task.getTaskStatus().toUpperCase());
                stmt.setString(6, (task.getTaskDueDate() == null || task.getTaskDueDate().isEmpty()) ? ""
                        : task.getTaskDueDate());

                stmt.execute();

                String returnCode = stmt.getString(1);

                if ("00".equals(returnCode)) {
                    return new ResponseData(TaskEnum.SUCCESS.getMessage(), TaskEnum.SUCCESS.getCode(), task);
                } else {

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
    public ResponseData delete(String taskId) {
        try (Connection conn = taskDatabase.connect()) {

            String sql = "{? = call public.delete_task_by_id(?)}";

            try (CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.registerOutParameter(1, Types.VARCHAR);

                stmt.setString(2, taskId);

                stmt.execute();

                String code = stmt.getString(1);

                switch (code) {
                    case "00":
                        return new ResponseData(TaskEnum.DELETED.getMessage(),
                                TaskEnum.SUCCESS.getCode(),
                                null);
                    case "01":
                        return new ResponseData(TaskEnum.NOT_FOUND.getMessage(),
                                TaskEnum.NOT_FOUND.getCode(),
                                null);
                    default:
                        return new ResponseData(TaskEnum.FAILED_OPERATION.getCode(),
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
