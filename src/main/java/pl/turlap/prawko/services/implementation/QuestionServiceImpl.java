package pl.turlap.prawko.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.mappers.CategoryMapper;
import pl.turlap.prawko.mappers.QuestionMapper;
import pl.turlap.prawko.models.*;
import pl.turlap.prawko.repositories.CategoryRepository;
import pl.turlap.prawko.repositories.LanguageRepository;
import pl.turlap.prawko.repositories.QuestionRepository;
import pl.turlap.prawko.services.QuestionService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final LanguageRepository languageRepository;

    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, LanguageRepository languageRepository, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.languageRepository = languageRepository;
        this.questionMapper = new QuestionMapper(this.languageRepository, categoryRepository);
    }

    @Override
    public List<QuestionDto> findAllQuestionsByLanguage(String language) {
        Language byNameOrCode = languageRepository.findByNameOrCode(language);
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> questionMapper.mapToQuestionDto(question, byNameOrCode)).collect(Collectors.toList());
    }

    @Override
    public Optional<QuestionDto> findById(Long id, String lang) {
        Language byNameOrCode = languageRepository.findByNameOrCode(lang);
        return questionRepository.findById(id).map(question -> questionMapper.mapToQuestionDto(question, byNameOrCode));
    }

    @Override
    public List<QuestionDto> findAllByTypeAndValue(QuestionType type, int value, Language language) {
        List<Question> questions = questionRepository.findQuestionsByTypeAndValue(type, value);
        return questions.stream().map(question -> questionMapper.mapToQuestionDto(question, language)).collect(Collectors.toList());
    }

    @Override
    public void saveAll(MultipartFile file) {
        List<Question> questions = questionMapper.mapCSVtoQuestions(file);
        questionRepository.saveAll(questions);
    }

    @Override
    public List<QuestionDto> findAllByType(QuestionType type, Language language) {
        List<Question> questions = questionRepository.findAllQuestionsByType(type);
        return questions.stream()
                .map(question -> questionMapper.mapToQuestionDto(question, language))
                .collect(Collectors.toList());
    }

}
