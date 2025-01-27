package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Language;

import java.util.List;

public interface TestService {

    List<QuestionDto> generateTest(Long userId, Language language, Category category);

}
