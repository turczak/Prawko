package pl.turlap.prawko.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    private Character label;
    private String content;
    private Boolean correct;
}