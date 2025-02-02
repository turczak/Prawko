package pl.turlap.prawko.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pl.turlap.prawko.exceptions.CustomAlreadyExistsException;
import pl.turlap.prawko.exceptions.CustomNotFoundException;

import java.util.Map;

@ControllerAdvice(annotations = RestController.class, assignableTypes = ViewController.class)
public class ExceptionController {

    @ExceptionHandler(CustomAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleException(CustomAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("field", exception.getFieldName(), "message", exception.getMessage()));
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleException(CustomNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("field", exception.getFieldName(), "message", exception.getMessage()));
    }

}