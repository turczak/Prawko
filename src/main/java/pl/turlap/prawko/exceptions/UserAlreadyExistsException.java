package pl.turlap.prawko.exceptions;

public class UserAlreadyExistsException extends CustomAlreadyExistsException {
    public UserAlreadyExistsException(String fieldName, String message) {
        super(fieldName, message);
    }
}