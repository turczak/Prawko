package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.mappers.QuestionMapper;
import pl.turlap.prawko.models.*;
import pl.turlap.prawko.repositories.QuestionRepository;
import pl.turlap.prawko.repositories.TestRepository;
import pl.turlap.prawko.repositories.UserRepository;
import pl.turlap.prawko.services.TestService;

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
    private final UserRepository userRepository;

    @Override
    public List<QuestionDto> generateTest(Long userId, Language language, Category category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        List<Question> basicQuestions = generateBasicQuestions(category);
        List<Question> specialQuestions = generateSpecialQuestions(category);

        List<Question> allQuestions = Stream.of(basicQuestions,
                        specialQuestions
                )
                .flatMap(Collection::stream)
                .toList();

        Test test = Test.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .questions(allQuestions)
                .build();
        user.getTests().add(test);
        userRepository.save(user);
        return test.getQuestions().stream()
                .map(question -> questionMapper.mapToQuestionDto(question, language))
                .toList();
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
