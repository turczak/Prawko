package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.models.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public List<String> toCategoryDto(List<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

}