package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.models.Category;

@Component
public class CategoryMapper {

    public String toDto(Category category){
        return category.getName();
    }

}