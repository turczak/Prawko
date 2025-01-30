package pl.turlap.prawko.exceptions;

import lombok.Getter;

@Getter
public class CustomAlreadyExistsException extends RuntimeException {
    private final String fieldName;

    public CustomAlreadyExistsException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}