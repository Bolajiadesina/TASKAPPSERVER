package com.moj.taskAppServer.models;

public enum TaskEnum {

    INVALID_ID("400", "The provided ID is invalid or does not exist."),
    BAD_REQUEST("400", "The server could not understand the request due to invalid syntax."),
    NULL_OBJECT("400", "One or more fields are null or invalid."),
    SERVER_ERROR("500", "The server encountered an internal error."),
    SUCCESS("00", "The request has been processed successfully."),
    FAILED_OPERATION("99", "The operation failed due to an internal error."),
    CREATED("00", "The request has been fulfilled and resulted in a new resource being created."),
    UPDATED("00", "The request has been fulfilled and the resource has been updated."),
    DELETED("00", "The request has been fulfilled and the resource has been deleted."),
    INSTANTIATION_EXCEPTION("409", "The server encountered an internal error."),
    NOT_FOUND("404", "The requested resource could not be found."),
    ILLEGAL_ACCESS_EXCEPTION("500", "The server encountered an internal error."),
    SQL_EXCEPTION("500", "The server encountered an internal error."),
    CLASS_NOT_FOUND_EXCEPTION("403", "The server encountered an internal error."),
    GENERIC_EXCEPTION("500", "An unexpected error occurred.");

    TaskEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
