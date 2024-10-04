package pl.turlap.prawko.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.mappers.QuestionMapper;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.repositories.QuestionRepository;
import pl.turlap.prawko.services.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionDto> findAllQuestionsByLanguage(Language language) {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> questionMapper.mapToQuestionDto(question, language)).collect(Collectors.toList());
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<QuestionDto> findALlByTypeAndValue(String type, int value, Language language) {
        List<Question> questions = questionRepository.findQuestionsByTypeAndValue(type, value);
        return questions.stream().map(question -> questionMapper.mapToQuestionDto(question, language)).collect(Collectors.toList());
    }
}
