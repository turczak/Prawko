package pl.turlap.prawko.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import pl.turlap.prawko.controller.ViewController;

import java.util.Map;

@ControllerAdvice(annotations = RestController.class, assignableTypes = ViewController.class)
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleException(CustomAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("field", exception.getFieldName(), "message", exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleException(CustomNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("field", exception.getFieldName(), "message", exception.getMessage()));
    }

}
