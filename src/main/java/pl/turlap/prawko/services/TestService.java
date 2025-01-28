package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.*;

import java.util.List;

public interface TestService {

    List<QuestionDto> generateTest(Long userId, Language language, Category category);

    void saveUserAnswer(Test test, Answer answer);

    Test findActiveUserTest(Long userId);

    void saveTest(Test test);

}
