package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.dto.TestDto;
import pl.turlap.prawko.exceptions.CustomNotFoundException;
import pl.turlap.prawko.mappers.QuestionMapper;
import pl.turlap.prawko.mappers.TestMapper;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.models.QuestionType;
import pl.turlap.prawko.models.Test;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.TestRepository;
import pl.turlap.prawko.services.AnswerService;
import pl.turlap.prawko.services.QuestionService;
import pl.turlap.prawko.services.TestService;
import pl.turlap.prawko.services.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final UserService userService;
    private final TestMapper testMapper;
    private final AnswerService answerService;

    @Override
    public List<TestDto> findAllByUserId(Long userId) {
        User user = userService.findById(userId);
        List<Test> allTests = testRepository.findAllByUserId(userId);
        return allTests.stream().map(test -> testMapper.toDto(test, user.getLanguage())).toList();
    }

    @Override
    public Test findById(Long testId) {
        return testRepository.findById(testId).orElseThrow(() -> new CustomNotFoundException("testId", "Test with id '" + testId + "' not found."));
    }

    @Override
    public TestDto getTestDto(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new CustomNotFoundException("testId", "Test with id '" + testId + "' not found"));
        Language language = test.getUser().getLanguage();
        return testMapper.toDto(test, language);
    }

    @Override
    public Test generateTest(Long userId) {
        User user = userService.findById(userId);
        Category category = user.getCategory();
        List<Question> allQuestions = Stream.of(generateBasicQuestions(category),
                        generateSpecialQuestions(category))
                .flatMap(Collection::stream)
                .toList();
        Test test = new Test()
                .withUser(user)
                .withQuestions(allQuestions)
                .withCreatedAt(LocalDateTime.now())
                .withIsActive(true);
        user.getTests().add(test);
        testRepository.save(test);
        return test;
    }

    private List<Question> generateSpecialQuestions(Category category) {
        Map<Integer, List<Question>> specialQuestions = questionService.findAllByCategoryAndType(
                        category, QuestionType.SPECJALISTYCZNY)
                .stream()
                .collect(Collectors.groupingBy(Question::getValue));
        return Stream.of(
                        selectRandomQuestions(specialQuestions.getOrDefault(
                                3, Collections.emptyList()), 6),
                        selectRandomQuestions(specialQuestions.getOrDefault(
                                2, Collections.emptyList()), 4),
                        selectRandomQuestions(specialQuestions.getOrDefault(
                                1, Collections.emptyList()), 2))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public void saveUserAnswer(Long testId, Long answerId) {
        Test test = findById(testId);
        Answer answer = answerService.findAnswerById(answerId);
        if (!test.getUserAnswers().contains(answer)) {
            test.getUserAnswers().add(answer);
            testRepository.save(test);
        }
    }

    private List<Question> generateBasicQuestions(Category category) {
        Map<Integer, List<Question>> basicQuestions = questionService.findAllByCategoryAndType(
                        category, QuestionType.PODSTAWOWY)
                .stream()
                .collect(Collectors.groupingBy(Question::getValue));
        return Stream.of(
                        selectRandomQuestions(basicQuestions.getOrDefault(
                                3, Collections.emptyList()), 10),
                        selectRandomQuestions(basicQuestions.getOrDefault(
                                2, Collections.emptyList()), 6),
                        selectRandomQuestions(basicQuestions.getOrDefault(
                                1, Collections.emptyList()), 4))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<Question> selectRandomQuestions(List<Question> questions, int count) {
        List<Question> modifableList = new ArrayList<>(questions);
        Collections.shuffle(modifableList);
        return modifableList.subList(0, Math.min(count, modifableList.size()));
    }

    @Override
    public void calculateResult(Long testId) {
        Test test = findById(testId);
        Integer score = 0;
        List<Question> questions = test.getQuestions();
        List<Answer> userAnswers = test.getUserAnswers();
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i).getIsCorrect()) {
                score += questions.get(i).getValue();
            }
        }
        test.setScore(score);
        test.setIsActive(false);
        testRepository.save(test);
    }

    @Override
    public QuestionDto selectQuestion(Long testId, Integer currentPage) {
        Test test = findById(testId);
        Language language = test.getUser().getLanguage();
        Question question = test.getQuestions().get(currentPage);
        return questionMapper.toDto(question, language);
    }

    @Override
    public Test randomQuestion(Long userId) {
        User user = userService.findById(userId);
        Question question = questionService.randomQuestion(user.getCategory());
        Test oneQuestionTest = new Test()
                .withUser(user)
                .withQuestions(List.of(question))
                .withCreatedAt(LocalDateTime.now())
                .withIsActive(true);
        user.getTests().add(oneQuestionTest);
        testRepository.save(oneQuestionTest);
        return oneQuestionTest;
    }
}
