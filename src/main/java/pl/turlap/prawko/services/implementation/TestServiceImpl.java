package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> generateTest(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Question> basicQuestions = generateBasicQuestions();
            List<Question> specialQuestions = generateSpecialQuestions();

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
            return ResponseEntity.ok("Test created.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    private List<Question> generateSpecialQuestions() {

        //6 pytań za 3 punkty
        List<Question> threePoints = questionRepository.findQuestionsByTypeAndValue(QuestionType.SPECJALISTYCZNY, 3);
        List<Question> threePointsQuestions = selectRandomQuestions(threePoints, 6);

        //4 pytania za 2 punkty
        List<Question> twoPoints = questionRepository.findQuestionsByTypeAndValue(QuestionType.SPECJALISTYCZNY, 2);
        List<Question> twoPointsQuestions = selectRandomQuestions(twoPoints, 4);

        //2 pytania za 1 punkt
        List<Question> onePoints = questionRepository.findQuestionsByTypeAndValue(QuestionType.SPECJALISTYCZNY, 1);
        List<Question> onePointsQuestions = selectRandomQuestions(onePoints, 2);

        return Stream.of(threePointsQuestions,
                        twoPointsQuestions,
                        onePointsQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Question> generateBasicQuestions() {

        //10 pytań za 3 punkty
        List<Question> threePoints = questionRepository.findQuestionsByTypeAndValue(QuestionType.PODSTAWOWY, 3);
        List<Question> threePointsQuestions = new ArrayList<>(selectRandomQuestions(threePoints, 10));

        //6 pytań za 2 punkty
        List<Question> twoPoints = questionRepository.findQuestionsByTypeAndValue(QuestionType.PODSTAWOWY, 2);
        List<Question> twoPointsQuestions = new ArrayList<>(selectRandomQuestions(twoPoints, 6));

        //4 pytania za 1 punkt
        List<Question> onePoints = questionRepository.findQuestionsByTypeAndValue(QuestionType.PODSTAWOWY, 1);
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

}
