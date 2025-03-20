package com.managerPasswords.api.domain.errors;

import org.springframework.http.HttpStatus;

public enum GlobalErrorEnum {



    // Resource-related errors
    RESOURCE_NOT_FOUND(2001, "The requested resource does not exist", HttpStatus.NOT_FOUND),
    ACCESS_DENIED(2002, "You do not have permission to access this resource", HttpStatus.FORBIDDEN),

    // Validation errors
    INVALID_DATA(3001, "The provided data is invalid", HttpStatus.BAD_REQUEST),
    INCORRECT_FORMAT(3002, "The data format is incorrect", HttpStatus.UNPROCESSABLE_ENTITY),

    // Server errors
    INTERNAL_SERVER_ERROR(4001, "An internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(4002, "The service is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    GlobalErrorEnum(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
