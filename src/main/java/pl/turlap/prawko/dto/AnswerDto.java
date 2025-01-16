package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Getter;

//answer in selected language
@Getter
@Builder
public class AnswerDto {

    private Character label;
    private String content;

}