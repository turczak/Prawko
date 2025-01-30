package pl.turlap.prawko.exceptions;

public class CategoryNotFoundException extends CustomNotFoundException {
    public CategoryNotFoundException(String message) {
        super("categoryName", message);
    }
}