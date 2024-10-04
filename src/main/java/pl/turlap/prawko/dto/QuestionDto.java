package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Getter;

//dto for one selected language
@Getter
@Builder
public class QuestionDto {

    private Long id;
    private String name;
    private String content;
    private String answer_A;
    private String answer_B;
    private String answer_C;
    private String correct;
    private String media;
    private String type;
    private Integer value;

}
