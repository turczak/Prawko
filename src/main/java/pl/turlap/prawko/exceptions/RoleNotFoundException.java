package pl.turlap.prawko.exceptions;

public class RoleNotFoundException extends CustomNotFoundException {
    public RoleNotFoundException(String fieldName, String message) {
        super(fieldName, message);
    }
}