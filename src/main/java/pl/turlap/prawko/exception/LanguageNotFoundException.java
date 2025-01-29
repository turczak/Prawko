package pl.turlap.prawko.exception;

public class LanguageNotFoundException extends RuntimeException{
    public LanguageNotFoundException(String message) {
        super(message);
    }

    public LanguageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
