package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Getter;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.models.QuestionTranslation;
import pl.turlap.prawko.models.QuestionType;

import java.util.List;

//dto for one selected language
@Getter
@Builder
public class QuestionDto {

    private String name;
    private QuestionTranslation questionTranslation;
    private List<AnswerDto> answers;
    private String media;
    private QuestionType type;
    private Integer value;

}