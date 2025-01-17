package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

//answer in selected language
@Data
@Builder
public class AnswerDto {

    private Character label;
    private String content;

}