package pl.turlap.prawko.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exception.CustomNotFoundException;
import pl.turlap.prawko.model.Category;
import pl.turlap.prawko.repository.CategoryRepository;
import pl.turlap.prawko.service.CategoryService;

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
