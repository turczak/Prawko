package pl.turlap.prawko.exception;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException {
    private final String fieldName;

    public CustomNotFoundException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
