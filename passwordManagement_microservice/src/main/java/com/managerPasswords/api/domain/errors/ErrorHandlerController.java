package com.managerPasswords.api.domain.errors;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
        return buildErrorResponse(GlobalErrorEnum.RESOURCE_NOT_FOUND);
    }


    private ResponseEntity<Map<String, Object>> buildErrorResponse(GlobalErrorEnum error) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("code", error.getCode());
        errorResponse.put("status", error.getHttpStatus().value());
        errorResponse.put("error", error.getHttpStatus().getReasonPhrase());
        errorResponse.put("message", error.getMessage());

        return new ResponseEntity<>(errorResponse, error.getHttpStatus());
    }

}
