package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.dto.TestDto;
import pl.turlap.prawko.models.Test;

import java.util.List;

public interface TestService {

    Test generateTest(Long userId);

    void saveUserAnswer(Long testId, Long answerId);

    Test findById(Long testId);

    TestDto showResult(Long testId);

    List<TestDto> findAllByUserId(Long userId);

    void calculateResult(Long testId);

    QuestionDto selectQuestion(Long testId, Integer currentPage);

}