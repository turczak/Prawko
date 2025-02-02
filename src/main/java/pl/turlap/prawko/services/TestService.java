package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.models.Test;

import java.util.List;

public interface TestService {

    Test generateTest(Long userId);

    void saveUserAnswer(Test test, Answer answer);

    Test findActiveUserTest(Long userId);

    void saveTest(Test test);

    List<QuestionDto> showQuestions(Long testId);

}
