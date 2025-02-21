package pl.turlap.prawko.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exception.CustomNotFoundException;
import pl.turlap.prawko.model.Answer;
import pl.turlap.prawko.repository.AnswerRepository;
import pl.turlap.prawko.service.AnswerService;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new CustomNotFoundException("answerId", "Answer with id '" + answerId + "' not found."));
    }

}
