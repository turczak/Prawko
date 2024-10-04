package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<QuestionDto> findAllQuestionsByLanguage(Language language);
    Optional<Question> findById(Long id);
    List<QuestionDto> findALlByTypeAndValue(String type, int value, Language language);
}
