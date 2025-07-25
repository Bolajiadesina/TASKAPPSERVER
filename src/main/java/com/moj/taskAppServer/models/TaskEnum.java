package com.moj.taskAppServer.models;



public enum TaskEnum {

    
        INVALID_ID("400", "The provided ID is invalid or does not exist."),
        BAD_REQUEST("400", "The server could not understand the request due to invalid syntax."),
        NULL_OBJECT("400", "One or more fields are null or invalid."),
        SERVER_ERROR("500", "The server encountered an internal error."),
        SUCCESS("00", "The request has been processed successfully."),
        FAILED_OPERATION("99", "The operation failed due to an internal error."),
        CREATED("00", "Task created successfully."),
        UPDATED("00", "Task update successfully."),
        DELETED("00", "Task deleted successfully."),
        INSTANTIATION_EXCEPTION("500", "The server encountered an internal error."),
        NOT_FOUND("01", "The Task could not be found."),
        ILLEGAL_ACCESS_EXCEPTION("500", "The server encountered an internal error."),
        SQL_EXCEPTION("500", "The server encountered an internal error."),
        CLASS_NOT_FOUND_EXCEPTION("500", "The server encountered an internal error."),
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
