package pl.turlap.prawko.exceptions;

public class UserNotFoundException extends CustomNotFoundException {
    public UserNotFoundException(String fieldName, String message) {
        super(fieldName, message);
    }
}