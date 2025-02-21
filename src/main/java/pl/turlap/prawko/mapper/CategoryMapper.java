package pl.turlap.prawko.mapper;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.model.Category;

@Component
public class CategoryMapper {

    public String toDto(Category category){
        return category.getName();
    }

}
