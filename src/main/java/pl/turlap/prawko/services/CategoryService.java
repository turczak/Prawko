package pl.turlap.prawko.services;

import pl.turlap.prawko.models.Category;

import java.util.List;

public interface CategoryService {

    Category findByName(String category);

    List<Category> findAll();

}
