package pl.turlap.prawko.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.turlap.prawko.models.QuestionType;

import java.util.List;

@With
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionDto {
    private String name;
    private String translation;
    private List<AnswerDto> answers;
    private String media;
    private QuestionType type;
    private Integer value;
    private List<String> categories;
}