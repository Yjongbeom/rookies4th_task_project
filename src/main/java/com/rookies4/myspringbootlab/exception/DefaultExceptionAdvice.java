package com.rookies4.myspringbootlab.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException ex) {
        ErrorObject error = new ErrorObject(ex.getMessage(), ex.getHttpStatus().value());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }
}