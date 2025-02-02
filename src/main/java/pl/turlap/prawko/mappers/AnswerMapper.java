package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.AnswerDto;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.services.AnswerService;

@Component
@AllArgsConstructor
public class AnswerMapper {
    private final AnswerService answerService;

    public AnswerDto toAnswerDto(Answer answer){
        return new AnswerDto()
                .withId(answer.getId())
                .withLabel(answer.getLabel());
    }

}