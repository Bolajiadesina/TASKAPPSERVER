package com.moj.taskAppServer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.moj.taskAppServer.models.Task;
import com.moj.taskAppServer.utils.StringUtilities;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskIdValidatorTest {

    @Autowired
    private StringUtilities util;

    @Test
    public void testValidTaskId() {
        // Valid UUID format
        assertTrue(util.validateTaskId("12345678-90ab-cdef-1234-567890abcdef"));
        assertTrue(util.validateTaskId("abcdef12-3456-7890-abcd-ef1234567890"));
        assertTrue(util.validateTaskId("ABCDEF12-3456-7890-ABCD-EF1234567890"));
        assertTrue(util.validateTaskId("00000000-0000-0000-0000-000000000000"));
    }

    @Test
    public void testInvalidTaskId() {
        // Null check
        assertFalse(util.validateTaskId(null));

        // Wrong formats
        assertFalse(util.validateTaskId("")); // empty string
        assertFalse(util.validateTaskId("12345678-90ab-cdef-1234-567890abcde")); // too short
        assertFalse(util.validateTaskId("12345678-90ab-cdef-1234-567890abcdef1")); // too long
        assertFalse(util.validateTaskId("1234567890abcdef1234567890abcdef")); // no hyphens
        assertFalse(util.validateTaskId("12345678-90ab-cdef-1234-567890abcdeg")); // invalid character 'g'
        assertFalse(util.validateTaskId("ghijklmn-opqr-stuv-wxyz-ghijklmnopqr")); // all invalid chars

        // Wrong hyphen positions
        assertFalse(util.validateTaskId("1234567890ab-cdef-1234-567890abcdef"));
        assertFalse(util.validateTaskId("12345678-90abcdef-1234-567890abcdef"));
        assertFalse(util.validateTaskId("12345678-90ab-cdef1234-567890abcdef"));
        assertFalse(util.validateTaskId("12345678-90ab-cdef-1234567890abcdef"));
    }

    @Test
    public void testEdgeCases() {

        assertTrue(util.validateTaskId("12aB45Cd-Ef89-Ab12-Cd34-EF56ab78cd90"));

        assertFalse(util.validateTaskId("12aB45Cd-Ef89-Ab12-Cd34-EF56ab78cd9g"));
    }

    @Test
    public void testreverseDate() {
        String result = "2025-06-28";
        String response = util.reverseDate(result);
        assertEquals("28-06-2025", response);
    }

    @Test
    public void testreverseDateWithNullInput() {
        String result = null;
        String response = util.reverseDate(result);
        assertEquals("", response);

    }

    @Test
    public void testreverseDateWithInvalidFormat() {
        String result = "2025/06/28";
        String response = util.reverseDate(result);
        assertEquals("2025/06/28", response);
    }

    @Test
    public void testValidateTaskWithValidTask() {
        Task task = new Task();
        task.setTaskId("12345678-90ab-cdef-1234-567890abcdef");
        task.setTaskName("Test Task");
        task.setTaskDescription("This is a test task.");
        task.setTaskStatus("PENDING");
        task.setTaskDueDate("2025-07-01");
        assertTrue(util.validateTask(task));
    }

    @Test
    public void testValidateTaskWithInvalidTask() {
        Task task = new Task();
        task.setTaskId("12345678-90ab-cdef-1234-567890abcdef");
        task.setTaskName(""); // Invalid: empty name
        task.setTaskDescription("This is a test task.");
        task.setTaskStatus("PENDING");
        task.setTaskDueDate("2025-07-01");
        assertFalse(util.validateTask(task));

    }

    @Test
    public void testValidateTaskWithNullTask() {
        Task task = null;
        assertFalse(util.validateTask(task));
    }

    @Test
    public void testValidateTaskWithEmptyTask() {
        Task task = new Task();
        task.setTaskId("");
        task.setTaskName("");
        task.setTaskDescription("");
        task.setTaskStatus("");
        task.setTaskDueDate("");
        assertFalse(util.validateTask(task));
    }

    @Test
    public void testValidateTaskWithIncompleteTask() {
        Task task = new Task();
        task.setTaskId("12345678-90ab-cdef-1234-567890abcdef");
        task.setTaskName("Test Task");
        // Missing description, status, and due date
        assertFalse(util.validateTask(task));
    }

    @Test
    public void testValidateTaskWithAllFieldsEmpty() {
        Task task = new Task();
        task.setTaskId("");
        task.setTaskName("");
        task.setTaskDescription("");
        task.setTaskStatus("");
        task.setTaskDueDate("");
        assertFalse(util.validateTask(task));

    }

    @Test
    public void testValidateTaskWithAllFieldsNull() {
        Task task = new Task();
        task.setTaskId(null);
        task.setTaskName(null);
        task.setTaskDescription(null);
        task.setTaskStatus(null);
        task.setTaskDueDate(null);
        assertFalse(util.validateTask(task));

    }

  
}