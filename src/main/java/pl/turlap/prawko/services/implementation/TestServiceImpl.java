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
import pl.turlap.prawko.repositories.QuestionRepository;
import pl.turlap.prawko.repositories.TestRepository;
import pl.turlap.prawko.services.LanguageService;
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
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final UserService userService;
    private final TestMapper testMapper;
    private final LanguageService languageService;

    @Override
    public List<QuestionDto> showQuestions(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new CustomNotFoundException("testId", "Test with id '" + testId + "' not found."));
        return test.getQuestions().stream().map(question -> questionMapper.toDto(question, test.getUser().getLanguage())).toList();
    }

    @Override
    public List<TestDto> findAllByUserId(Long userId) {
        User user = userService.findById(userId);
        List<Test> allTests = testRepository.findAllByUserId(userId);
        return allTests.stream().map(test -> testMapper.toDto(test, user.getLanguage())).toList();
    }

    @Override
    public TestDto findById(Long testId, String languageNameOrCode) {
        Language language = languageService.findByNameOrCode(languageNameOrCode);
        Test test = testRepository.findById(testId).orElseThrow(() -> new CustomNotFoundException("testId", "Test with id '" + testId + "' not found."));
        return testMapper.toDto(test, language);
    }

    @Override
    public Test generateTest(Long userId) {
        User user = userService.findById(userId);
        List<Question> basicQuestions = generateBasicQuestions(user.getCategory());
        List<Question> specialQuestions = generateSpecialQuestions(user.getCategory());

        List<Question> allQuestions = Stream.of(basicQuestions,
                        specialQuestions
                )
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

        List<Question> questions = questionRepository.findAllByCategoryAndType(category, QuestionType.SPECJALISTYCZNY);

        // 6 questions with 3 points value
        List<Question> threePointsQuestions = questions.stream().filter(question -> question.getValue() == 3).toList();
        List<Question> shuffledThreePointsQuestions = selectRandomQuestions(threePointsQuestions, 6);

        // 4 questions with 2 points value
        List<Question> twoPointsQuestions = questions.stream().filter(question -> question.getValue() == 2).toList();
        List<Question> shuffledTwoPointsQuestions = selectRandomQuestions(twoPointsQuestions, 4);

        // 2 questions with 1 points value
        List<Question> onePointsQuestions = questions.stream().filter(question -> question.getValue() == 1).toList();
        List<Question> shuffledOnePointsQuestions = selectRandomQuestions(onePointsQuestions, 2);

        return Stream.of(shuffledOnePointsQuestions,
                        shuffledTwoPointsQuestions,
                        shuffledThreePointsQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Test findActiveUserTest(Long userId) {
        return testRepository.findActiveUserTest(userId);
    }

    @Override
    public void saveTest(Test test) {
        testRepository.save(test);
    }

    @Override
    public void saveUserAnswer(Test test, Answer answer) {
        if (!test.getUserAnswers().contains(answer)) {
            test.getUserAnswers().add(answer);
            testRepository.save(test);
        }
    }

    private List<Question> generateBasicQuestions(Category category) {

        List<Question> questions = questionRepository.findAllByCategoryAndType(category, QuestionType.PODSTAWOWY);

        // 10 questions with 3 points value
        List<Question> threePointsQuestions = questions.stream().filter(question -> question.getValue() == 3).toList();
        List<Question> shuffledThreePointsQuestions = selectRandomQuestions(threePointsQuestions, 10);

        // 6 questions with 2 points value
        List<Question> twoPointsQuestions = questions.stream().filter(question -> question.getValue() == 2).toList();
        List<Question> shuffledTwoPointsQuestions = selectRandomQuestions(twoPointsQuestions, 6);

        // 4 questions with 1 points value
        List<Question> onePointsQuestions = questions.stream().filter(question -> question.getValue() == 1).toList();
        List<Question> shuffledOnePointsQuestions = selectRandomQuestions(onePointsQuestions, 4);

        return Stream.of(shuffledOnePointsQuestions,
                        shuffledTwoPointsQuestions,
                        shuffledThreePointsQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Question> selectRandomQuestions(List<Question> questions, int count) {
        List<Question> modifableList = new ArrayList<>(questions);
        Collections.shuffle(modifableList);
        return modifableList.subList(0, Math.min(count, modifableList.size()));
    }

}
