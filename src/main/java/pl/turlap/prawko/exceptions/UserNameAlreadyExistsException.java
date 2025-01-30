package pl.turlap.prawko.exceptions;

public class UserNameAlreadyExistsException extends CustomAlreadyExistsException {
    public UserNameAlreadyExistsException(String message) {
        super("userName", message);
    }
}