package pl.turlap.prawko.exceptions;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException {
    private final String fieldName;

    public CustomNotFoundException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}

