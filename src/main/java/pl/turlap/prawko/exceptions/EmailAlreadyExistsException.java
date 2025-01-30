package pl.turlap.prawko.exceptions;

public class EmailAlreadyExistsException extends CustomAlreadyExistsException {
    public EmailAlreadyExistsException(String message) {
        super("email", message);
    }
}