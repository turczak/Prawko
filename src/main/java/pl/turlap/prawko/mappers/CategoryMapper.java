package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.CategoryDto;
import pl.turlap.prawko.models.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public List<CategoryDto> toCategoryDto(List<Category> categories) {
        return categories.stream()
                .map(category -> CategoryDto.builder()
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
    }

}
