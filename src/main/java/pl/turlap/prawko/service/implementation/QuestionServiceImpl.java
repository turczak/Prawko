package pl.turlap.prawko.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.exception.CustomNotFoundException;
import pl.turlap.prawko.mapper.QuestionMapper;
import pl.turlap.prawko.model.Language;
import pl.turlap.prawko.model.Question;
import pl.turlap.prawko.model.QuestionType;
import pl.turlap.prawko.repository.QuestionRepository;
import pl.turlap.prawko.service.CSVService;
import pl.turlap.prawko.service.LanguageService;
import pl.turlap.prawko.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final LanguageService languageService;

    private final QuestionMapper questionMapper;

    private final CSVService csvService;

    @Override
    public List<QuestionDto> findAllQuestionsByLanguage(String language) {
        Language byNameOrCode = languageService.findByNameOrCode(language);
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> questionMapper.toDto(question, byNameOrCode)).collect(Collectors.toList());
    }

    @Override
    public Optional<QuestionDto> findById(Long id, String lang) {
        Language byNameOrCode = languageService.findByNameOrCode(lang);
        return questionRepository.findById(id).map(question -> questionMapper.toDto(question, byNameOrCode));
    }

    @Override
    public List<QuestionDto> findAllByTypeAndValue(QuestionType type, int value, Language language) {
        List<Question> questions = questionRepository.findQuestionsByTypeAndValue(type, value);
        return questions.stream().map(question -> questionMapper.toDto(question, language)).collect(Collectors.toList());
    }

    @Override
    public void saveAll(MultipartFile file) {
        List<Question> questions = csvService.mapCSVtoQuestions(file);
        questionRepository.saveAll(questions);
    }

    @Override
    public List<QuestionDto> findAllByType(String type, String languageNameOrCode) {
        Language language = languageService.findByNameOrCode(languageNameOrCode);
        QuestionType questionType;
        switch (type.toUpperCase()){
            case "BASIC" -> {
                questionType = QuestionType.PODSTAWOWY;
            }
            case "SPECIAL" ->{
                questionType = QuestionType.SPECJALISTYCZNY;
            }
            default -> throw new CustomNotFoundException("questionType", "Question type '" + type + "' not found.");
        }
        return questionRepository.findAllQuestionsByType(questionType).stream()
                .map(question -> questionMapper.toDto(question, language))
                .collect(Collectors.toList());
    }

}
