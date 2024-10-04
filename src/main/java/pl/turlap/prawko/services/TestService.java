package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.models.Test;

import java.util.List;

public interface TestService {
    List<QuestionDto> generateTest(Language language);
    void saveTest(Test test);
}
