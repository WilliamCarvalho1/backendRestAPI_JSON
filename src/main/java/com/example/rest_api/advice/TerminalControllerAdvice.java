package com.example.rest_api.advice;

import com.example.rest_api.exception.BadRequestException;
import com.example.rest_api.exception.CustomNotFoundException;
import com.example.rest_api.exception.JsonValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TerminalControllerAdvice {
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<String> handleCustomNotFoundException(CustomNotFoundException notFoundException){
        return new ResponseEntity<>("There's no Terminal with this Logic. Please, try an existing one.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException badRequestException){
        return new ResponseEntity<>("Please, try a valid Logic.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonValidationException.class)
    public ResponseEntity<String> handleJsonValidationException(JsonValidationException jsonValidationException){
        return new ResponseEntity<>("Please fix your json! \n" + jsonValidationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}