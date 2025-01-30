package pl.turlap.prawko.exceptions;

public class LanguageNotFoundException extends CustomNotFoundException {
    public LanguageNotFoundException(String message) {
        super("languageCode", message);
    }
}