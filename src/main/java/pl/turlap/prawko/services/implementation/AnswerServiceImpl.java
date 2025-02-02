package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exceptions.CustomNotFoundException;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.repositories.AnswerRepository;
import pl.turlap.prawko.services.AnswerService;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new CustomNotFoundException("answerId", "Answer with id '" + answerId + "' not found."));
    }

}
