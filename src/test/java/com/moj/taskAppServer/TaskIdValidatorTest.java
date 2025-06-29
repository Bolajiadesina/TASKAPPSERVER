package com.moj.taskAppServer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.moj.taskAppServer.utils.StringUtilities;

import jakarta.validation.constraints.AssertTrue;

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
    public void testreverseDate(){
        String result = "2025-06-28";
        String response= util.reverseDate(result);
        assertEquals("28-06-2025", response);
    }
}