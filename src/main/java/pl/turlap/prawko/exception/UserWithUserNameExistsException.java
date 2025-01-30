package pl.turlap.prawko.exception;

public class UserWithUserNameExistsException extends UserAlreadyExistsException {
    public UserWithUserNameExistsException(String message) {
        super("userName", message);
    }
}