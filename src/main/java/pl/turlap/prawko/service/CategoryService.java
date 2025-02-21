package pl.turlap.prawko.service;

import pl.turlap.prawko.model.Category;

import java.util.List;

public interface CategoryService {

    Category findByName(String category);

    List<Category> findAll();

}
