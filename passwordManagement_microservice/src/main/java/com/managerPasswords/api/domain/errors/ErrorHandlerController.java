package com.managerPasswords.api.domain.errors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;




@RestControllerAdvice
public class ErrorHandlerController {


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GlobalError> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new GlobalError(GlobalErrorEnum.RESOURCE_NOT_FOUND.getMessage()));
    }

}
