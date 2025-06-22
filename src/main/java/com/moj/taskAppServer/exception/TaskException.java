package com.moj.taskAppServer.exception;

public class TaskException extends RuntimeException {
    private String message;
    private int statusCode;

    public TaskException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void SQLStateException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;

    }

    public void NullpointerException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public void IllegalArgumentException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "TaskException{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
