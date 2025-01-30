package pl.turlap.prawko.exception;

public class UserWithEmailExistsException extends UserAlreadyExistsException {
    public UserWithEmailExistsException(String message) {
        super("email", message);
    }
}