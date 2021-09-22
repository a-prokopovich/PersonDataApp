package com.prokopovich.persondata.webapp.controller;

import com.prokopovich.persondata.domain.exception.PersonServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(PersonServiceException.class)
    public ResponseEntity<String> handlePersonServiceException(PersonServiceException e) {

        log.error("Exception: " + e.getMessage(), e.getCause());
        var jsonResponse = ("Exception: " + e.getMessage());

        return new ResponseEntity<>(jsonResponse, HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error("Exception: " + e.getMessage(), e.getCause());
        var jsonResponse = ("Exception: " + e.getMessage());

        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
    }
}
