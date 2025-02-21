package pl.turlap.prawko.service;

import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.model.Language;
import pl.turlap.prawko.model.QuestionType;

import java.util.List;
import java.util.Optional;


public interface QuestionService {

    List<QuestionDto> findAllQuestionsByLanguage(String language);

    Optional<QuestionDto> findById(Long id, String language);

    void saveAll(MultipartFile file);

    List<QuestionDto> findAllByTypeAndValue(QuestionType type, int value, Language language);

    List<QuestionDto> findAllByType(String type, String language);

}
