package pl.turlap.prawko.exceptions;

public class RoleAlreadyExistsException extends CustomAlreadyExistsException {
    public RoleAlreadyExistsException(String message) {
        super("roleName", message);
    }
}