package com.task_management_system.task.exception;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>(false, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleResourceNotFoundException(ConfigDataResourceNotFoundException ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<String>(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleGenericException(Exception ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<String>(false, "An unexpected error occurred " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleEndPointNotFoundException(NoHandlerFoundException ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<String>(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse<String>> handleBadCredentialException(BadCredentialsException ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<String>(false, "Authentication failed. Please check your credentials.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}