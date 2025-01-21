package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Data;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.QuestionType;

import java.util.List;

@Data
@Builder
public class QuestionDto {

    private String name;
    private String questionTranslation;
    private List<AnswerDto> answers;
    private String media;
    private QuestionType type;
    private Integer value;
    private List<Category> categories;

}