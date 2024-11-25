package com.demo.bookstore.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ErrorInfo> handleErrorResponseException(ErrorResponseException e) {
        if (e.getCode() != null) {
            return new ResponseEntity(new ErrorInfo(e.getMessage()), e.getCode());
        } else {
            return new ResponseEntity(new ErrorInfo(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({BindException.class, ValidationException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, MissingPathVariableException.class})
    public ResponseEntity<ErrorInfo> handleValidateException(Exception e) {
//        log.error("Validate Error!", e);
        String errorMessage = null;
        if (e instanceof MethodArgumentNotValidException) {
            FieldError error = ((MethodArgumentNotValidException) e).getFieldError();
            if (error != null) {
                errorMessage = "[" + error.getField() + "] " + error.getDefaultMessage();
            }
        } else if (e instanceof BindException) {
            FieldError error = ((BindException) e).getFieldError();
            if (error != null) {
                errorMessage = "[" + error.getField() + "] " + error.getDefaultMessage();
            }
        } else {
            errorMessage = e.getMessage();
        }
        return ResponseEntity.badRequest().body(new ErrorInfo(errorMessage));
    }

    public ResponseEntity<ErrorInfo> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity(new ErrorInfo(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
