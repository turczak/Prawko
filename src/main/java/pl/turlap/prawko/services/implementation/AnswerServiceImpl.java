package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.repositories.AnswerRepository;
import pl.turlap.prawko.services.AnswerService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public Answer findAnswerById(Long answerId) {
        Optional<Answer> byId = answerRepository.findById(answerId);
        return byId.orElse(null);
    }

}
