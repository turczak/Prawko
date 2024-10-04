package pl.turlap.prawko.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.mappers.QuestionMapper;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.models.Test;
import pl.turlap.prawko.repositories.QuestionRepository;
import pl.turlap.prawko.repositories.TestRepository;
import pl.turlap.prawko.services.TestService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TestServiceImpl implements TestService {


    private TestRepository testRepository;
    private QuestionRepository questionRepository;
    private QuestionMapper questionMapper;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public List<QuestionDto> generateTest(Language language) {

        List<Question> basicQuestions = generateBasicQuestions();
        List<Question> specialQuestions = generateSpecialQuestions();

        List<Question> allQuestions = Stream.of(basicQuestions,
                        specialQuestions
                )
                .flatMap(Collection::stream)
                .toList();
        return allQuestions.stream().map(question -> questionMapper.mapToQuestionDto(question, language)).collect(Collectors.toList());
    }

    private List<Question> generateSpecialQuestions() {

        //6 pytań za 3 punkty
        List<Question> threePoints = questionRepository.findQuestionsByTypeAndValue("SPECJALISTYCZNY", 3);
        List<Question> threePointsQuestions = new ArrayList<>(selectRandomQuestions(threePoints, 6));

        //4 pytania za 2 punkty
        List<Question> twoPoints = questionRepository.findQuestionsByTypeAndValue("SPECJALISTYCZNY", 2);
        List<Question> twoPointsQuestions = new ArrayList<>(selectRandomQuestions(twoPoints, 4));

        //2 pytania za 1 punkt
        List<Question> onePoints = questionRepository.findQuestionsByTypeAndValue("SPECJALISTYCZNY", 1);
        List<Question> onePointsQuestions = new ArrayList<>(selectRandomQuestions(onePoints, 2));

        return Stream.of(threePointsQuestions,
                        twoPointsQuestions,
                        onePointsQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Question> generateBasicQuestions() {

        //10 pytań za 3 punkty
        List<Question> threePoints = questionRepository.findQuestionsByTypeAndValue("PODSTAWOWY", 3);
        List<Question> threePointsQuestions = new ArrayList<>(selectRandomQuestions(threePoints, 10));

        //6 pytań za 2 punkty
        List<Question> twoPoints = questionRepository.findQuestionsByTypeAndValue("PODSTAWOWY", 2);
        List<Question> twoPointsQuestions = new ArrayList<>(selectRandomQuestions(twoPoints, 6));

        //4 pytania za 1 punkt
        List<Question> onePoints = questionRepository.findQuestionsByTypeAndValue("PODSTAWOWY", 1);
        List<Question> onePointsQuestions = new ArrayList<>(selectRandomQuestions(onePoints, 4));

        return Stream.of(threePointsQuestions,
                        twoPointsQuestions,
                        onePointsQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Question> selectRandomQuestions(List<Question> questions, int count) {
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(count, questions.size()));
    }

    @Override
    public void saveTest(Test test) {
        testRepository.save(test);
    }
}
