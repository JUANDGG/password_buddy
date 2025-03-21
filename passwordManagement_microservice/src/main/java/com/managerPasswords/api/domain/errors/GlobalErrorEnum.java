package com.managerPasswords.api.domain.errors;

import org.springframework.http.HttpStatus;

public enum GlobalErrorEnum {



    // Resource-related errors
    RESOURCE_NOT_FOUND(2001, "The requested resource does not exist", HttpStatus.NOT_FOUND),
    ACCESS_DENIED(2002, "You do not have permission to access this resource", HttpStatus.FORBIDDEN),

    //jwt validatios
    JWT_ISEMPTY(401,"jwt is null",HttpStatus.FORBIDDEN);



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
