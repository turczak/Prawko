package pl.turlap.prawko.services;

import pl.turlap.prawko.models.Category;

public interface CategoryService {

    Category findByName(String category);

}
