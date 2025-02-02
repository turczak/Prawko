package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exceptions.CustomNotFoundException;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.repositories.CategoryRepository;
import pl.turlap.prawko.services.CategoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new CustomNotFoundException("categoryName","Category: " + categoryName + " not found."));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}