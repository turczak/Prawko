package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.AnswerDto;
import pl.turlap.prawko.models.Answer;

@Component
@AllArgsConstructor
public class AnswerMapper {

    public AnswerDto toDto(Answer answer){
        return new AnswerDto()
                .withId(answer.getId())
                .withLabel(answer.getLabel());
    }

}